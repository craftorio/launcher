<?php
define('INCLUDE_CHECK',true);
include("connect.php");
include_once("loger.php");
@$get = $_GET['user'];
list($md5) = explode('?', $get);
	try {
		$stmt = $db->prepare("SELECT user,md5 FROM usersession WHERE md5= :md5");
		$stmt->bindValue(':md5', $md5);
		$stmt->execute();
		$row = $stmt->fetch(PDO::FETCH_ASSOC);
		$realUser = $row['user'];
		$time = time();
		$file = $capeurl.$realUser.'.png';
		$exists = remoteFileExists($file);
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
					"url":"'.$skinurl.$realUser.'.png"
				}'.$cape.'
			}
		}';
		echo '
		{
			"id":"'.$md5.'","name":"'.$realUser.'","properties":
			[
			{
				"name":"textures","value":"'.base64_encode($base64).'"
			}
			]
		}';
	} catch(PDOException $pe) {
			die("Ошибка".$logger->WriteLine($log_date.$pe));  //вывод ошибок MySQL в m.log
	}