<?php

defined('BASEPATH') or exit('No direct script access allowed');

class Webview extends MX_Controller
{
    public function __construct()
    {
        parent::__construct();
        date_default_timezone_set('Asia/Jakarta');

        $this->load->database();
        $this->load->helper(array('url', 'libs', 'alert'));
        $this->load->library(array('form_validation', 'session', 'alert', 'breadcrumbs'));

    }

    public function _page_output($output = null)
    {
        $this->load->view('master_page.php', (array) $output);
    }

}