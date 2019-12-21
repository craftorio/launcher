<?php
ob_start();
    define('INCLUDE_CHECK',true);
    include_once("loger.php");
    $postdata = file_get_contents("php://input");
    if (($_SERVER['REQUEST_METHOD'] == 'POST' ) && (stripos($_SERVER["CONTENT_TYPE"], "application/json") === 0)) {
        $json = json_decode($postdata, true);
    }
    
    $md5       = @$json['selectedProfile'];
    $sessionid = @$json['accessToken'];
    $serverid  = @$json['serverId'];
    $bad = array('error' => "Bad login",'errorMessage' => "Bad login");
    try {
        if (!preg_match("/^[a-zA-Z0-9_-]+$/", $md5) || !preg_match("/^[a-zA-Z0-9:_\-]+$/", $sessionid) || !preg_match("/^[a-zA-Z0-9_\-]+$/", $serverid)){
            echo (json_encode($bad));
        } else {
            include("connect.php");
            $stmt = $db->prepare("Select md5,user From usersession WHERE md5= :md5 AND SESSION= :sessionid");
            $stmt->bindValue(':md5', $md5);
            $stmt->bindValue(':sessionid', $sessionid);
            $stmt->execute();
            $row      = $stmt->fetch(PDO::FETCH_ASSOC);
            $realmd5  = $row['md5'];
            $realUser = $row['user'];
            $ok       = ['id' => $realmd5, 'name' => $realUser];
            if ($realmd5 == $md5) {
                $stmt = $db->prepare("Update usersession SET server= :serverid Where session = :sessionid And md5 = :md5");
                $stmt->bindValue(':md5', $md5);
                $stmt->bindValue(':sessionid', $sessionid);
                $stmt->bindValue(':serverid', $serverid);
                $stmt->execute();

                echo json_encode($ok);
            } else {
                echo json_encode($bad);
            }
        }
    } catch(PDOException $pe) {
        echo ("bad".$logger->WriteLine($log_date.$pe));
    }
$content = ob_get_clean();

//file_put_contents('/tmp/j.php.log.' . time(), $content);

echo $content;
