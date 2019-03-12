<?php
namespace app\forms;

use std, gui, framework, app;

class repo_bot extends AbstractForm {

    /**
     * @event addbot.action 
     */
    function doAddbotAction(UXEvent $e = null) {    
        $this->addBot($this->token->text , $this->listbot);
        $this->token->clear();
    }

    /**
     * @event installMyToken.action 
     */
    function doInstallMyTokenAction(UXEvent $e = null) {
        $api = new jTelegramApi();
        $api->connectToTelegram($this); //Подключение к телеграмму ;)
    }
}
