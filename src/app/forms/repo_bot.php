<?php
namespace app\forms;

use std, gui, framework, app;

class repo_bot extends AbstractForm {

    /**
     * @event addbot.action 
     */
    function doAddbotAction(UXEvent $e = null) {    
        $this->addBot ($this->token->text , $this->listbot);
        $this->token->clear();
    }
}