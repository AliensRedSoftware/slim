<?php
namespace app\forms;

use std, gui, framework, app;

class MainForm extends AbstractForm {

    /**
     * @event repo_bot.action 
     */
    function doRepo_botAction(UXEvent $e = null) {
        $this->showPreloader('Ожидание ответа от формы...');
        $this->form('repo_bot')->showAndWait();
        $this->hidePreloader();
    }
}