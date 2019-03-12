<?php
namespace app\modules;

use std, gui, framework, app;

class jump extends AbstractModule {

    /**
     * Шифровать данный текст 
     */
     public function decode ($text) {
         $char = [
             'я' , 'ф' , 'й' , 'ч' , 'ы' , 'ц'
         ];
         $count = str::length($text); //Колво букакв в тексте
         $context = null; //Декодированный текст
         for ($i = 0; $i < $count; $i++) {
             foreach ($char as $value) {
                 $str = str::pos($text , $value);
                 if ($str == 0) { //Если была найден символ то генерируем из него другую букакаву
                     if ($value == 'я') {
                         $context .= '!';
                     }
                 }
             }
         }
         return $context; 
     }
}