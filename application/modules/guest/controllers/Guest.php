<?php

defined('BASEPATH') or exit('No direct script access allowed');

class Guest extends MX_Controller
{
    private $user_id;
    private $base_breadcrumbs;

    public function __construct()
    {
        parent::__construct();
        date_default_timezone_set('Asia/Jakarta');

        $this->load->database();
        $this->load->helper(array('url', 'libs', 'alert'));
        $this->load->library(array('form_validation', 'session', 'alert', 'breadcrumbs'));

        $this->breadcrumbs->load_config('default');

        $level                  = $this->session->userdata('user_level');
        $this->user_id          = $this->session->userdata('user_id');
        $this->base_breadcrumbs = '/guest';

        if ($level !== 'GUEST') {
            redirect(site_url('signout'), 'reload');
        }

        $this->output->set_header('Last-Modified: ' . gmdate('D, d M Y H:i:s') . ' GMT');
        $this->output->set_header('Cache-Control: no-store, no-cache, must-revalidate, no-transform, max-age=0, post-check=0, pre-check=0');
        $this->output->set_header('Pragma: no-cache');
        $this->output->set_header('Expires: Mon, 26 Jul 1997 05:00:00 GMT');
    }

    public function _page_output($output = null)
    {

        $output['user_id']      = $this->user_id;
        $output['nama_lengkap'] = $this->session->userdata('user_username');
        $this->load->view('master_page.php', (array) $output);
    }

    public function index()
    {
        $this->breadcrumbs->push('Dashboard', $this->base_breadcrumbs);

        $data['page_name']  = 'beranda';
        $data['page_title'] = 'Beranda';
        $this->_page_output($data);
    }

    public function jurnal_kelas($status_ajax = null)
    {

        $data = array();

        $data['keterangan'] = "<br/>";
        $data['keterangan'] .= "Jam selesai ditandai dengan warna header tabel hijau<br/>";
        $data['keterangan'] .= "Jam aktif ditandai dengan warna header tabel kuning<br/>";
        $data['keterangan'] .= "Kode Warna: <div style='display:inline' class='text-danger'>Merah</div> => belum isi jurnal; <div style='display:inline' class='text-success'>Hijau</div> => Guru Hadir;<div style='display:inline' class='text-warning'>Kuning</div> => Jam Kosong; <div style='display:inline' class='text-info'>Biru</div> => Digantikan";

        if ($status_ajax == null) {

            $this->breadcrumbs->push('Dashboard', $this->base_breadcrumbs);
            $this->breadcrumbs->push('Jurnal Kelas', $this->base_breadcrumbs . '/jurnal_kelas');

            $data['page_name']  = 'jurnal_kelas';
            $data['page_title'] = 'Jurnal Kelas';
            $this->_page_output($data);

        } else {

            header('content-type: application/json');

            $day        = date('w');
            $hari_array = array('MINGGU', 'SENIN', 'SELASA', 'RABU', 'KAMIS', 'JUMAT', 'SABTU', 'MINGGU');

            $hari_sekarang = $hari_array[$day];

            $date_now = date('Y-m-d');
            $time_now = date('H:i:s');

            if (cek_libur($date_now) === 'YA') {

                echo json_encode(
                    array(
                        'jurnal_kelas' => $this->load->view('ajax_jurnal_kelas_libur', null, true),
                    )
                );

            } else {

                $jam_aktif = $this->db->query("SELECT jam_ke FROM jam_aktif WHERE mulai <= '$time_now' ORDER BY jam_ke DESC LIMIT 1");

                $jurnal = $this->db->query("
                SELECT a.kelas_id,
                       r.nama_kelas,
                       IFNULL(j.`status`,'PENDING') AS status_jam_1,
                       IFNULL(k.`status`,'PENDING') AS status_jam_2,
                       IFNULL(l.`status`,'PENDING') AS status_jam_3,
                       IFNULL(m.`status`,'PENDING') AS status_jam_4,
                       IFNULL(n.`status`,'PENDING') AS status_jam_5,
                       IFNULL(o.`status`,'PENDING') AS status_jam_6,
                       IFNULL(p.`status`,'PENDING') AS status_jam_7,
                       IFNULL(q.`status`,'PENDING') AS status_jam_8,
                      
                       IFNULL(rr.`status`,'PENDING') AS status_jam_9,
                       IFNULL(ss.`status`,'PENDING') AS status_jam_10,

                       CONCAT(b.mp,' - ', b.pengampu) AS jam_1,
                       CONCAT(c.mp,' - ', c.pengampu) AS jam_2,
                       CONCAT(d.mp,' - ', d.pengampu) AS jam_3,
                       CONCAT(e.mp,' - ', e.pengampu) AS jam_4,
                       CONCAT(f.mp,' - ', f.pengampu) AS jam_5,
                       CONCAT(g.mp,' - ', g.pengampu) AS jam_6,
                       CONCAT(h.mp,' - ', h.pengampu) AS jam_7,
                       CONCAT(i.mp,' - ', i.pengampu) AS jam_8,

                       CONCAT(tt.mp,' - ', tt.pengampu) AS jam_9,
                       CONCAT(uu.mp,' - ', uu.pengampu) AS jam_10
                FROM jadwal_mengajar a

                LEFT JOIN (SELECT a.id,b.nama AS mp, c.nama_lengkap AS pengampu,a.kelas_id
                           FROM jadwal_mengajar a
                              LEFT JOIN matapelajaran b ON a.matapelajaran_id = b.id
                              LEFT JOIN pegawai c ON a.pegawai_id = c.id
                              WHERE a.jam LIKE '%1%' AND a.hari = '$hari_sekarang') b ON a.kelas_id = b.kelas_id
                LEFT JOIN jurnal j ON b.id  = j.jadwal_mengajar_id AND j.tanggal = '$date_now'

                LEFT JOIN (SELECT a.id,b.nama AS mp, c.nama_lengkap AS pengampu,a.kelas_id
                           FROM jadwal_mengajar a
                              LEFT JOIN matapelajaran b ON a.matapelajaran_id = b.id
                              LEFT JOIN pegawai c ON a.pegawai_id = c.id
                              WHERE a.jam LIKE '%2%' AND a.hari = '$hari_sekarang') c ON a.kelas_id = c.kelas_id
                LEFT JOIN jurnal k ON c.id  = k.jadwal_mengajar_id AND k.tanggal = '$date_now'

                LEFT JOIN (SELECT a.id,b.nama AS mp, c.nama_lengkap AS pengampu,a.kelas_id
                           FROM jadwal_mengajar a
                              LEFT JOIN matapelajaran b ON a.matapelajaran_id = b.id
                              LEFT JOIN pegawai c ON a.pegawai_id = c.id
                              WHERE a.jam LIKE '%3%' AND a.hari = '$hari_sekarang') d ON a.kelas_id = d.kelas_id
                LEFT JOIN jurnal l ON d.id  = l.jadwal_mengajar_id AND l.tanggal = '$date_now'

                LEFT JOIN (SELECT a.id,b.nama AS mp, c.nama_lengkap AS pengampu,a.kelas_id
                           FROM jadwal_mengajar a
                              LEFT JOIN matapelajaran b ON a.matapelajaran_id = b.id
                              LEFT JOIN pegawai c ON a.pegawai_id = c.id
                              WHERE a.jam LIKE '%4%' AND a.hari = '$hari_sekarang') e ON a.kelas_id = e.kelas_id
                LEFT JOIN jurnal m ON e.id  = m.jadwal_mengajar_id AND m.tanggal = '$date_now'

                LEFT JOIN (SELECT a.id,b.nama AS mp, c.nama_lengkap AS pengampu,a.kelas_id
                           FROM jadwal_mengajar a
                              LEFT JOIN matapelajaran b ON a.matapelajaran_id = b.id
                              LEFT JOIN pegawai c ON a.pegawai_id = c.id
                              WHERE a.jam LIKE '%5%' AND a.hari = '$hari_sekarang') f ON a.kelas_id = f.kelas_id
                LEFT JOIN jurnal n ON f.id  = n.jadwal_mengajar_id AND n.tanggal = '$date_now'

                LEFT JOIN (SELECT a.id,b.nama AS mp, c.nama_lengkap AS pengampu,a.kelas_id
                           FROM jadwal_mengajar a
                              LEFT JOIN matapelajaran b ON a.matapelajaran_id = b.id
                              LEFT JOIN pegawai c ON a.pegawai_id = c.id
                              WHERE a.jam LIKE '%6%' AND a.hari = '$hari_sekarang') g ON a.kelas_id = g.kelas_id
                LEFT JOIN jurnal o ON g.id  = o.jadwal_mengajar_id AND o.tanggal = '$date_now'

                LEFT JOIN (SELECT a.id,b.nama AS mp, c.nama_lengkap AS pengampu,a.kelas_id
                           FROM jadwal_mengajar a
                              LEFT JOIN matapelajaran b ON a.matapelajaran_id = b.id
                              LEFT JOIN pegawai c ON a.pegawai_id = c.id
                              WHERE a.jam LIKE '%7%' AND a.hari = '$hari_sekarang') h ON a.kelas_id = h.kelas_id
                LEFT JOIN jurnal p ON h.id  = p.jadwal_mengajar_id AND p.tanggal = '$date_now'

                LEFT JOIN (SELECT a.id,b.nama AS mp, c.nama_lengkap AS pengampu,a.kelas_id
                           FROM jadwal_mengajar a
                              LEFT JOIN matapelajaran b ON a.matapelajaran_id = b.id
                              LEFT JOIN pegawai c ON a.pegawai_id = c.id
                              WHERE a.jam LIKE '%8%' AND a.hari = '$hari_sekarang') i ON a.kelas_id = i.kelas_id
                LEFT JOIN jurnal q ON i.id  = q.jadwal_mengajar_id AND q.tanggal = '$date_now'


                LEFT JOIN (SELECT a.id,b.nama AS mp, c.nama_lengkap AS pengampu,a.kelas_id
                           FROM jadwal_mengajar a
                              LEFT JOIN matapelajaran b ON a.matapelajaran_id = b.id
                              LEFT JOIN pegawai c ON a.pegawai_id = c.id
                              WHERE a.jam LIKE '%9%' AND a.hari = '$hari_sekarang') tt ON a.kelas_id = tt.kelas_id
                LEFT JOIN jurnal rr ON tt.id  = rr.jadwal_mengajar_id AND rr.tanggal = '$date_now'


                LEFT JOIN (SELECT a.id,b.nama AS mp, c.nama_lengkap AS pengampu,a.kelas_id
                           FROM jadwal_mengajar a
                              LEFT JOIN matapelajaran b ON a.matapelajaran_id = b.id
                              LEFT JOIN pegawai c ON a.pegawai_id = c.id
                              WHERE a.jam LIKE '%10%' AND a.hari = '$hari_sekarang') uu ON a.kelas_id = uu.kelas_id
                LEFT JOIN jurnal ss ON uu.id  = ss.jadwal_mengajar_id AND ss.tanggal = '$date_now'

                LEFT JOIN kelas r ON a.kelas_id = r.id

                WHERE a.hari = '$hari_sekarang'
                GROUP BY a.kelas_id");

                if ($jam_aktif->num_rows() > 0) {
                    $jam_ke = $jam_aktif->row_array();

                    echo json_encode(
                        array(
                            'jurnal_kelas' => $this->load->view('ajax_jurnal_kelas',
                                array(
                                    'jurnal' => $jurnal,
                                    'jam_ke' => $jam_ke['jam_ke'],
                                ), true),
                        )
                    );
                } else {

                    echo json_encode(
                        array(
                            'jurnal_kelas' => $this->load->view('ajax_jurnal_kelas',
                                array(
                                    'jurnal' => $jurnal,
                                    'jam_ke' => 0,
                                ), true),
                        )
                    );

                }

            }

        }

    }

}
