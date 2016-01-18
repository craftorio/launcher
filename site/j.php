<?php
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
            exit(json_encode($bad));
        }
        include("connect.php");
        $stmt = $db->prepare("Select md5,user From usersession WHERE md5= :md5 AND SESSION= :sessionid");
        $stmt->bindValue(':md5', $md5);
        $stmt->bindValue(':sessionid', $sessionid);
        $stmt->execute();
        $row      = $stmt->fetch(PDO::FETCH_ASSOC);
        $realmd5  = $row['md5'];
        $realUser = $row['user'];
        $ok = array('id' => $realmd5, 'name' => $realUser);
        if($realmd5 == $md5) {
            $stmt = $db->prepare("Update usersession SET server= :serverid Where session = :sessionid And md5 = :md5");
            $stmt->bindValue(':md5', $md5);
            $stmt->bindValue(':sessionid', $sessionid);
            $stmt->bindValue(':serverid', $serverid);
            $stmt->execute();
            #if($stmt->rowCount() == 1) {
                echo json_encode($ok);
                file_put_contents(__DIR__ . "/Sucess_" . $realUser, 1);
                exit;
            #}
            
        }
        echo json_encode($bad);
    } catch(PDOException $pe) {
        die("bad".$logger->WriteLine($log_date.$pe));
    }
