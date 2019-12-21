<?php
define('INCLUDE_CHECK',true);
include_once("loger.php");
$gender = 'male';
@$md5 = $_GET['user'];
try {
    if (!preg_match("/^[a-zA-Z0-9_-]+$/", $md5)){
        exit;
    }
    include("connect.php");
    $stmt = $db->prepare("SELECT user FROM usersession WHERE md5= :md5");
    $stmt->bindValue(':md5', $md5);
    $stmt->execute();
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    $realUser = strtolower($row['user']);
    if($realUser==null) {
        exit;
    }
    $time = time();
    $file1 = $capeurl.$realUser.'.png';
    $exists1 = file_exists($uploaddirp.'/'.$realUser.'.png');
    $file2 = $skinurl.$realUser.'.png';
    $exists2 = true;

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
    }

    if ($exists1) {
        $cape =
    '
            "CAPE":
            {
                "url":"'.$capeurl.'?/'.$realUser.'$"
            }';
    } else {
        $cape = '';
    }
    if ($exists2) {
        $fromSkin =
    '
            "SKIN":
            {
                "url":"'.$skinUrl.'"
            }';
    } else {
        $fromSkin = '';
    }
    if ($exists1 && $exists2) {
        $spl = ',';
    } else {
        $spl = '';
    }

    $base64 ='
    {
        "timestamp":"'.$time.'","profileId":"'.$md5.'","profileName":"'.$realUser.'","textures":
        {
            '.$fromSkin.$spl.$cape.'
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
} catch(PDOException $pe) {
        die($logger->WriteLine($log_date.$pe));  //вывод ошибок MySQL в m.log
}
