<?php
include_once("uuid.php");
foreach($_REQUEST as $key => $val)
echo '{"id":"'.uuidConvert(substr($key, 10)).'","name":"'.substr($key, 10).'"}';