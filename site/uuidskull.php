<?php
include_once("uuid.php");
foreach($_REQUEST as $key => $val)
$user = str_replace(array('[', ']'), array('', ''), $key);
echo '{"id":"'.uuidConvert($user).'","name":'.$user.'}';