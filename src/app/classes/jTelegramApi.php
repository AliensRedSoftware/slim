<?php
namespace app\classes;

use php\gui\UXTrayNotification;
use cURLFile;
use cURL;
use bundle\jurl\jURL;
use facade\Json;
use std, gui, framework, app;

class jTelegramApi {

    public $chatid , $text;
    
    /**
     * Установка токена 
     */
    public function setToken ($token) {
        Logger::info("Установлен токен =>$token");
        $GLOBALS['token_telegram'] = "https://api.telegram.org/bot$token/";
    }
    
    /**
     * Возвращает токен yandex 
     */
    public function getTokenYandex() {
        return 'trnsl.1.1.20180201T145128Z.7f95bedf8c21f20e.9cdaeb397a733c1d978aebaba9307b2aad4db1f2';
    }
    /**
     * Установка прокси
     * @return string
     */
    public function setProxy (jURL $ch , $proxy) {
        $form = app()->getForm(Settings);
        if ($form->checkboxproxytelegram->selected) { 
            Logger::info("Установлен прокси ->$proxy");
            $ch->setProxy($proxy);
        }
    }

    /**
     * Задать прошлый id сообщение
     * @return string
     */
    public function setlastmessage ($string) {
        $GLOBALS['lastmessage'] = $string;
    }
    
    /**
     * Возвратить прошлый id сообщение
     * @return string
     */
    public function getlastmessage () {
        return $GLOBALS['lastmessage'];
    }
        
    
    /**
     * Возвращение стикера id
     * @return string 
     */
    public function getstickerid () {
        return $GLOBALS['stickerid_telegram'];
    }
    
    /**
     * Возвращение стикера id
     */
    public function setstickerid ($id) {
        $GLOBALS['stickerid_telegram'] = $id;
    }
    
    /**
    * Подключение к телеграмму сервесу
    * Форма с токеном
    */
    public function connectToTelegram (repo_bot $formToken) {
        $enabled = $formToken->installMyToken->selected;
        $token = $formToken->myToken->text;
        if (trim($token) == null) {
            Logger::error('Пустой токен не возможно установить так как он пустой');
            $formToken->installMyToken->selected = false;
            $formToken->myToken->clear();
            return ;
        }
        if ($enabled) {
            Logger::info("Аккаунт Telegram_api => ожидание");
            $formToken->showPreloader('Аккаунт Telegram_api => ожидание');
            $method = 'getMe';//Установка метода
            $this->setToken($token);//Установка токена
            $url_request = $GLOBALS['token_telegram'] . $method;//Установка запроса     
            $request = new jURL($url_request);
            //$this->setProxy($request , $form->proxyTelegram->text); //Установка прокси
            $request->asyncExec(function ($data) use ($formToken) { //Запрс на проверку токена
                $response = Json::decode($data);
                if ($response['ok'] == true) {
                    Logger::info('Аккаунт Telegram_api => OK');
                    $formToken->toast('Аккаунт Telegram_api => OK');
                    $formToken->hidePreloader();
                    $this->requestTelegram($formToken);
                } else {
                    Logger::error('Аккаунт Telegram_api => ERROR TOKEN!');
                    $formToken->toast('Аккаунт Telegram_api => ERROR TOKEN!');
                    $formToken->hidePreloader();
                    $formToken->installMyToken->selected = false;
                }
            });
            $request->close();
        } else {
            Logger::info('Деактивация Telegram_api => OK');
            $formToken->toast('Деактивация Telegram_api => OK');
            $formToken->hidePreloader();
        }
    }
    
    /**
     * Получить chatid
     * @return string 
     */
    public function getChatid() {
        return app()->getForm(Settings)->chatid->text;
    }
    
    /**
     * Установка chatid
     * @return int
     */
    public function setChatid ($chatid) {
        app()->getForm(Settings)->chatid->text = $chatid;
    }
    
    /**
    * Запрос телеграмм
    */
    function requestTelegram (repo_bot $form) {
        $enabled = $form->installMyToken->selected;
        if ($enabled) {
            $method = 'getUpdates?offset=-1';
            $url_request = $GLOBALS['token_telegram'] . $method;
            $request = new jURL($url_request);
            //$this->setProxy($request , $form->proxyTelegram->text);//Установка прокси
            $request->asyncExec(function ($data) use ($form) {
                $response = Json::decode($data);
                $message_id = $response['result'][0]['message']['message_id'];
                $message = $response['result'][0]['message']['text'];
                $Echatid = $response['result'][0]['message']['chat']['id'];
                $GLOBALS['name'] = '@' . $response['result'][0]['message']['from']['username'];
                //Установка стикера
                $stickid = $response['result'][0]['message']['sticker']['file_id'];
                /*
                if ($form->chatid_look->selected && $this->getChatid() != $Echatid) {
                    $this->requestTelegram($form);
                    return ;
                } else {
                    $this->setChatid($response['result'][0]['message']['chat']['id']);
                }
                */
                if ($stickid != null && $stickid != $this->getstickerid()) {
                    Logger::info('Установка id стикера =>' . $stickid);
                    $this->setstickerid($stickid);
                }
                if ($message_id != $GLOBALS['message_id'] && $message != null) {
                    $GLOBALS['message_id'] = $message_id;
                    $jump = new jump(); //Джамп шифровщик
                    $text = $jump->decode($message);
                    Logger::info($text);
                }
                $this->requestTelegram($form);//Выполнить цикл команды
            });
        }
    }
    
    public function ArrayeachLine ($Array) {
            $f = null;
        foreach ($Array as $key) {
            $f .= urlencode($key . "\r\n");
        }
        return $f;
    }
    
    /**
     * Возвращает лист модулей
     * @return string 
     */
    public function getListModules () {
        $modules = fs::scan('modules/Telegram_api/', ['extensions' => ['php'], 'excludeDirs' => true]);
        $text[] = null;
        foreach ($modules as $module) {
            if ($module != null) {
                array_push($text , str::replace($module , '\\' , '/'));
            }
        }
        return $text;
    }
    
    /**
     * Отправить текст
     * @return string
     */
    public function sendMessage_id ($chat_id , $text) {
        $form = app()->getForm(Settings);
        $url_request = $GLOBALS['token_telegram'] . "sendMessage?chat_id=$chat_id&text=$text";
        $request = new jURL($url_request);
        if ($form->checkboxproxytelegram->selected) {
            $this->setProxy($request , $form->proxyTelegram->text);
        }
        if ($form->notificationtelegram->selected) {
            $notification = new UXTrayNotification();
            $notification->notificationType = $form->typetelegramnotification->selected;
            $notification->animationType = $form->animationnotificationtelegram->selected;
            $notification->title = 'Новое сообщение ! :)';
            $notification->message = urldecode($text);
            $notification->location = $form->positionnotificationtelegram->selected;
            $notification->show();
        }
        $request->asyncExec(function(){});
        $request->close();
    }
    
    /**
     * Отправить фото
     * @return string
     */
    public function sendPhoto_id ($chat_id , $photo) {
        $form = app()->getForm(Settings);
        $url = $GLOBALS['token_telegram'] . "sendPhoto";
        $ch = new jURL($url);
        if ($form->checkboxproxytelegram->selected) {
            $this->setProxy($ch , $form->proxyTelegram->text);
        }
        $ch->addPostFile('photo', fs::abs($photo));
        $ch->setPostData(['chat_id' => $chat_id]);
        $ch->asyncExec(function (){});
        $ch->close();
    }
    
    /**
     * Отправить фото url
     * @return string
     */
    public function sendPhotoByUrl ($chat_id , $url) {
        $form = app()->getForm(Settings);
        $url_request = $GLOBALS['token_telegram'] . "sendPhoto?chat_id=$chat_id&photo=$url";
        $request = new jURL($url_request);
        if ($form->checkboxproxytelegram->selected) {
            $this->setProxy($request , $form->proxyTelegram->text);
        }
        $request->asyncExec(function(){});
        $request->close();
    }
    
    /**
     * Отправить Документ
     * @return string
     */
    public function sendDocument_id ($chat_id , $Document) {
        $form = app()->getForm(Settings);
        $url = $GLOBALS['token_telegram'] . "sendDocument";
        $ch = new jURL($url);
        if ($form->checkboxproxytelegram->selected) {
            $this->setProxy($ch , $form->proxyTelegram->text);
        }
        $ch->addPostFile('document', fs::abs($Document));
        $ch->setPostData(['chat_id' => $chat_id]);
        $ch->asyncExec(function (){});
        $ch->close();
    }
    
    /**
     * Отправить Массив строку
     */
    public function sendArrayText_id ($chat_id , $ArrayText) {
        $form = app()->getForm(Settings);
        $text = null;
        foreach ($ArrayText as $value) {
            $text .= urldecode($value) . urlencode("\r\n");
        }
        $url_request = $GLOBALS['token_telegram'] . "sendMessage?chat_id=$chat_id&text=$text";
        $request = new jURL($url_request);
        if ($form->checkboxproxytelegram->selected) {
            $this->setProxy($request , $form->proxyTelegram->text);
        }
        $request->asyncExec(function () {});
        $request->close();
    }
    
    /**
     * Отправить текст как TextМемо 
     */
    public function sendEachText_id ($chat_id , $textMemo , $count) {
        $form = app()->getForm(Settings);
        $ArrayText = str_split($textMemo , $count);
        $text = null;
        foreach ($ArrayText as $value) {
            $text .= $value . "\r";
        }
        $url_request = $GLOBALS['token_telegram'] . "sendMessage?chat_id=$chat_id&text=" . urlencode(trim($text));
        $request = new jURL($url_request);
        if ($form->checkboxproxytelegram->selected) {
            $this->setProxy($request , $form->proxyTelegram->text);
        }
        $request->asyncExec(function () {});
        $request->close();
    }
    
    /**
     * Отправка стикера
     */
    public function sendSticker_id ($chat_id , $id) {
        $form = app()->getForm(Settings);
        $url_request = $GLOBALS['token_telegram'] . "sendSticker?chat_id=$chat_id&sticker=$id";
        $request = new jURL($url_request);
        if ($form->checkboxproxytelegram->selected) {
            $this->setProxy($request , $form->proxyTelegram->text);
        }
        $request->asyncExec(function(){});
        $request->close();
    }
    
    public function eachLine ($array , $int) {
        $text = str_split($array , $int);
        $f = null;
        foreach ($text as $key) {
            $f .= $key . "\r\n";
        }
        return $f;
    }
    
    public function ArrayeachLine ($Array) {
            $f = null;
        foreach ($Array as $key) {
            $f .= urlencode($key . "\r\n");
        }
        return $f;
    }
}