<?php
define('INCLUDE_CHECK',true);
include_once("connect.php");
include_once("uuid.php");
@$user = json_decode($HTTP_RAW_POST_DATA);
    echo '[{"id":"'.str_replace('-', '', @uuidConvert($realUser)).'","name":"'.$realUser.'"}]';
} catch(PDOException $pe) {
    die($logger->WriteLine($log_date.$pe));  //вывод ошибок MySQL в m.log
}