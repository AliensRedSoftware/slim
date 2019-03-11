<?php
namespace app\modules;

use std, gui, framework, app;

class MainModule extends AbstractModule {
    
    /**
     * Добавить нового бота в репу 
     */
    public function addBot ($token , UXListView $listToken) {
        if (trim($token) != null) {
            foreach ($listToken->items->toArray() as $value) {
                if ($token == $value) {
                    Logger::error("[token => $token] Такой токен уже есть!");
                    return ; //Остоновить код и не читать дальше так как ошибка...
                }
            }
            Logger::info("[token => $token] Токен добавлен");
            $listToken->items->add($token); //Добавить токен в лист токенов
        } else {
            Logger::error('Поля токен пусто :(');
        }
    }
}