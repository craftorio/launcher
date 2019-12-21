<?php
error_reporting(0);
define('INCLUDE_CHECK',true);
include_once("loger.php");
@$user     = $_GET['username'];
@$serverid = $_GET['serverId'];
$bad = array('error' => "Bad login",'errorMessage' => "Bad login");
try {
    if (!preg_match("/^[a-zA-Z0-9_-]+$/", $user) || !preg_match("/^[a-zA-Z0-9_-]+$/", $serverid)){
        exit(json_encode($bad));
    }
    include ("connect.php");
    $stmt = $db->prepare("SELECT user,md5,skin FROM usersession WHERE user = :user and server = :serverid");
    $stmt->bindValue(':user', $user);
    $stmt->bindValue(':serverid', $serverid);
    $stmt->execute();
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    $realUser = $row['user'];
    $userSkin = $row['skin'] ? $row['skin'] : $row['user'];
    $md5 = $row['md5'];

    $skinFile = null;
    $skinDir = $uploaddirs . DIRECTORY_SEPARATOR . strtolower($realUser);
    if (is_dir($skinDir)) {
        foreach (scandir($skinDir) as $file) {
            if (is_file($skinDir . DIRECTORY_SEPARATOR. $file)) {
                $skinFile = $file;
                break;
            }
        }
    }

    if (!is_null($skinFile)) {
        $skinUrl = $skinurl . strtolower($realUser) . "/" . $skinFile;
    } else {
        $gender = 'male';
        if (isset($genderapi_key) && $genderapi_key) {
            $stmt = $db->prepare("SELECT $db_columnMail FROM $db_table WHERE $db_columnUser= :login");
            $stmt->bindValue(':login', $realUser);
            $stmt->execute();
            $stmt->bindColumn($db_columnMail, $realUserEmail);
            $stmt->fetch();
            $genderInfo = (array) json_decode(file_get_contents("https://genderapi.io/api/email?email={$realUserEmail}&key={$genderapi_key}"));
            $gender = isset($genderInfo['gender']) ? $genderInfo['gender'] : null;
            if (!$gender) {
                $genderInfo = (array) json_decode(file_get_contents("https://genderapi.io/instagram/?q={$realUser}&key={$genderapi_key}"));
                $gender = isset($genderInfo['gender']) ? $genderInfo['gender'] : null;
            }
            if (!$gender) {
                $genderInfo = (array) json_decode(file_get_contents("https://genderapi.io/api/?name={$realUser}&key={$genderapi_key}"));
                $gender = isset($genderInfo['gender']) ? $genderInfo['gender'] : null;
            }
            if (!$gender) {
                $gender = 'male';
            }
        }
        $fromSkin = __DIR__ . "/DefaultSkins/{$gender}/" . rand(1, 20) . '.png';
        $skinFile = md5_file($fromSkin). '.png';
        if (!is_dir($uploaddirs . '/' . strtolower($realUser))) {
            mkdir($uploaddirs . '/' . strtolower($realUser), 0755, true);
        }
        copy ($fromSkin, $uploaddirs . '/' . strtolower($realUser).'/' . $skinFile);
        $skinUrl = $skinurl . strtolower($realUser) . "/" . $skinFile;
        //$skinUrl = $defskinurl . 'steve.png';
    }

    if($user == $realUser)
    {
        $time = time();
        $file = $capeurl.$realUser.'.png';
        $exists = file_exists($uploaddirp.'/'.$realUser.'.png');
        if ($exists) {
            $cape =
        ',
                "CAPE":
                {
                    "url":"'.$capeurl.'?/'.$realUser.'$"
                }';
        } else {
            $cape = '';
        }
        $base64 ='
        {
            "timestamp":"'.$time.'","profileId":"'.$md5.'","profileName":"'.$realUser.'","textures":
            {
                "SKIN":
                {
                    "url":"'.$skinUrl.'"
                }'.$cape.'
            }
        }';
        echo '
        {
            "id":"'.$md5.'","name":"'.$realUser.'","properties":
            [
            {
                "name":"textures","value":"'.base64_encode($base64).'","signature":"Cg=="
            }
            ]
        }';

    }
    else exit(json_encode($bad));
} catch(PDOException $pe) {
        die("Ошибка".$logger->WriteLine($log_date.$pe));  //вывод ошибок MySQL в m.log
}
