<?php
define('INCLUDE_CHECK',true);
include_once("loger.php");
$realUser = strtolower(@$_GET['player']);
include("connect.php");
if ($realUser == null) {
    exit;
}
$time    = time();
$file1   = $capeurl . $realUser . '.png';
$exists1 = file_exists($uploaddirp . '/' . $realUser . '.png');
$file2   = $skinurl . $realUser . '.png';
$exists2 = true;
$gender = 'male';
$skinFile = null;
$skinDir  = $uploaddirs . DIRECTORY_SEPARATOR . strtolower($realUser);
if (is_dir($skinDir)) {
    foreach (scandir($skinDir) as $file) {
        if (is_file($skinDir . DIRECTORY_SEPARATOR . $file)) {
            $skinFile = $file;
            break;
        }
    }
}

if (!is_null($skinFile)) {
    $skinUrl = $skinurl . strtolower($realUser) . "/" . $skinFile;
} else {
    try {
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

    } catch (Exception $e) {
        $gender = 'male';
    }

    $fromSkin = __DIR__ . "/DefaultSkins/{$gender}" . rand(1, 20) . '.png';
    $skinFile = md5_file($fromSkin) . '.png';
    copy($fromSkin, $uploaddirs . '/' . strtolower($realUser) . '/' . $skinFile);
    $skinUrl = $skinurl . strtolower($realUser) . $skinFile;
}

header('Content-Type: image/png');

echo file_get_contents($uploaddirs . '/' . strtolower($realUser).'/' . $skinFile);
    
