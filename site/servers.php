<?php
// 1-> Имя папки клиента 
// 2-> ip 
// 3-> port 
// 4-> Версия клиента
$servers = [
    'IndustrialStrike, server.craftorio.com, 25565, 1.12.2<::>',
    #'IndustrialForest, server.craftorio.com, 25567, 1.12.2<::>',
];

echo implode(PHP_EOL, $servers);
