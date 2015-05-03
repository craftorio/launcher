<?php
define('INCLUDE_CHECK',true);
include("connect.php");
include_once("loger.php");
@$md5 = $_GET['user'];
	try {
		if (!preg_match("/^[a-zA-Z0-9_-]+$/", $md5)){
			exit;
		}
		$stmt = $db->prepare("SELECT user,md5 FROM usersession WHERE md5= :md5");
		$stmt->bindValue(':md5', $md5);
		$stmt->execute();
		$row = $stmt->fetch(PDO::FETCH_ASSOC);
		$realUser = $row['user'];
		if($realUser==null) {
			exit;
		}
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