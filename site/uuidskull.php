<?php
include_once("uuid.php");
echo '{"id":"'.uuidConvert(substr($key, 10)).'","name":"'.substr($key, 10).'"}';