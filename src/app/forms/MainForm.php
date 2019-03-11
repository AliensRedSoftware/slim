<?php
namespace app\forms;

use std, gui, framework, app;

class MainForm extends AbstractForm {

    /**
     * @event buttonAlt.action 
     */
    function doButtonAltAction(UXEvent $e = null) {    
        $this->form('register')->show();
    }
}