<?php
/** {license_text} */

define('INCLUDE_CHECK',true);
include_once("../../../loger.php");
$user = trim(strtolower(@$_GET['username']));
$serverId = @$_GET['serverId'];
$rootDir = realpath(__DIR__ . '/../../../');
try {
    include("../../../connect.php");
    $stmt = $db->prepare("SELECT user, md5 FROM usersession WHERE user=:user AND server=:server");
    $stmt->bindValue(':user', $user);
    $stmt->bindValue(':server', $serverId);
    $stmt->execute();
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    $realUser = $row['user'];
    $md5 = $row['md5'];
    if($realUser == null) {
        exit;
    }
    $time = time();
    $file1 = $capeurl.$realUser.'.png';
    $exists1 = file_exists($uploaddirpPath.'/'.$realUser.'.png');
    $file2 = $skinurl.$realUser.'.png';
    $exists2 = true;

    $skinFile = null;
    $skinDir = $uploaddirsPath . DIRECTORY_SEPARATOR . strtolower($realUser);
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
        $fromSkin = $rootDir . '/DefaultSkins/' . rand(1, 20) . '.png';
        $skinFile = md5_file($fromSkin). '.png';
        if (!is_dir($uploaddirsPath . '/' . strtolower($realUser))) {
            mkdir($uploaddirsPath . '/' . strtolower($realUser), 0755, true);
        }
        copy ($fromSkin, $uploaddirsPath . '/' . strtolower($realUser).'/' . $skinFile);
        $skinUrl = $skinurl . strtolower($realUser) . $skinFile;
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
