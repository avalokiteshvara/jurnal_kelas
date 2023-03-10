<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css" />
<div class="row">
   <div class="col-md-2">
      <div class="card mb-3" style="max-width: 20rem;">
         <div class="card-header text-white bg-primary">Filter</div>
         <div class="card-body">
            <form method="POST" action="" autocomplete="off">
               <div class="form-group date">
                  <label for="exampleInputEmail1">Tanggal Awal</label>
                  <input name="tgl_awal" value="<?php echo set_value('tgl_awal')?>" type="text" class="form-control"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
               </div>
               <div class="form-group date">
                  <label for="exampleInputEmail1">Tanggal Akhir</label>	
                  <input name="tgl_akhir" value="<?php echo set_value('tgl_akhir')?>" type="text" class="form-control"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
               </div>
               <div class="form-group">
                  <label for="exampleInputEmail1">Kelas</label>	
                  <select class="form-control" name="kelas_id">
                     <?php foreach ($kelas->result_array() as $key) { ?>
                     <option  <?php echo set_select('kelas_id', $key['id'], False);?> value="<?=$key['id'];?>"><?=$key['nama_kelas'];?></option>
                     <?php } ?>				  	
                  </select>
               </div>
               <button type="submit" class="btn btn-block btn-success">Tampilkan</button>
            </form>
         </div>
      </div>
   </div>
   <div class="col-md-10">
      <ul class="nav nav-tabs" id="myTab" role="tablist">
         <li class="nav-item">
            <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">Details</a>
         </li>
         <li class="nav-item">
            <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Rekap</a>
         </li>
      </ul>
      <div class="tab-content" id="myTabContent">
         <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
            <br>	
            <table class="groceryCrudTable table table-hover table-striped table-bordered" id="datatable">
               <thead  class="thead-dark">
                  <tr>
                     <th scope="col">Tanggal</th>
                     <th scope="col" class="text-center">Hari</th>
                     <th scope="col" class="text-center">Jam</th>
                     <th scope="col" class="text-center">Nama Guru</th>
                     <th scope="col" class="text-center">Matapelajaran</th>
                     <th scope="col" class="text-center">Status</th>
                     <th scope="col" class="text-center">Topik</th>
                     <th scope="col" class="text-center">Aksi</th>
                  </tr>
               </thead>
               <tbody>
                  <?php if(!empty($_POST)){ ?>
                  <?php foreach ($jurnal->result_array() as $key) { ?>
                  <tr>
                     <td><?=$key['tanggal'];?></td>
                     <td><?=$key['hari'];?></td>
                     <td><?=$key['jam'];?></td>
                     <td><?=$key['nama_guru'];?></td>
                     <td><?=$key['pelajaran'];?></td>
                     <td><?=$key['status'];?></td>
                     <td><?=$key['topik'];?></td>
                     <td><a href="<?php echo site_url('kurikulum/edit_jurnal_kelas/edit/' . $key['id']);?>">Ubah</a></td>
                  </tr>
                  <?php } ?>
                  <?php } ?>
               </tbody>
            </table>
         </div>
         <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
            <br>	
            <table class="groceryCrudTable table table-hover table-striped table-bordered" id="datatable_rekap">
               <thead  class="thead-dark">
                  <tr>
                     <th scope="col">Nama kelas</th>
                     <th scope="col" class="text-center">Pending</th>
                     <th scope="col" class="text-center">Hadir</th>
                     <th scope="col" class="text-center">Digantikan</th>
                     <th scope="col" class="text-center">Kosong</th>
                  </tr>
               </thead>
               <tbody>
                  <?php if(!empty($_POST)){ ?>
                  <?php foreach ($rekap->result_array() as $key) { ?>
                  <tr>
                     <td><?=$key['nama_kelas'];?></td>
                     <td><?=$key['pending'];?></td>
					 <td><?=$key['hadir'];?></td>
                     <td><?=$key['digantikan'];?></td>
                     <td><?=$key['kosong'];?></td>
                  </tr>
                  <?php } ?>
                  <?php } ?>
               </tbody>
            </table>
         </div>
      </div>
   </div>
</div>
<div class="modal fade" id="modal_kehadiran" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
   <div class="modal-dialog" role="document">
      <div class="modal-content">
         <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Detail Kehadiran</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
            </button>
         </div>
         <div class="modal-body">
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Tutup</button>
            <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
         </div>
      </div>
   </div>
</div>
<script>
   $('.date').datepicker({
       todayHighlight: true,
       language: "id",
       format: "yyyy-mm-dd"
   }).on('changeDate', function(e){
       $(this).datepicker('hide');
   });
   
</script>