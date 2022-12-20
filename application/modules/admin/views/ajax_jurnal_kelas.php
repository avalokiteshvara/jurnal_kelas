<?php foreach ($jurnal->result_array() as $row) { ?>
  <tr>
    <th scope="row"><?=$row['nama_kelas']?></th>
    <td class="<?=jurnal_row_color($row['status_jam_1']);?> text-center"><?=$row['jam_1'];?></td>
    <td class="<?=jurnal_row_color($row['status_jam_2']);?> text-center"><?=$row['jam_2'];?></td>
    <td class="<?=jurnal_row_color($row['status_jam_3']);?> text-center"><?=$row['jam_3'];?></td>
    <td class="<?=jurnal_row_color($row['status_jam_4']);?> text-center"><?=$row['jam_4'];?></td>
    <td class="<?=jurnal_row_color($row['status_jam_5']);?> text-center"><?=$row['jam_5'];?></td>
    <td class="<?=jurnal_row_color($row['status_jam_6']);?> text-center"><?=$row['jam_6'];?></td>
    <td class="<?=jurnal_row_color($row['status_jam_7']);?> text-center"><?=$row['jam_7'];?></td>
    <td class="<?=jurnal_row_color($row['status_jam_8']);?> text-center"><?=$row['jam_8'];?></td>
    <td class="<?=jurnal_row_color($row['status_jam_9']);?> text-center"><?=$row['jam_9'];?></td>
    <td class="<?=jurnal_row_color($row['status_jam_10']);?> text-center"><?=$row['jam_10'];?></td>
  </tr>
<?php } ?>

<script>
    <?php for ($i=1; $i <= 10; $i++) { ?>
      $('#th_<?=$i;?>').removeClass("bg-success");
      $('#th_<?=$i;?>').removeClass("bg-warning");
    <?php } ?>

    <?php for ($i=1; $i <= $jam_ke; $i++) {
      if($i == $jam_ke){
        echo "$('#th_" . $i. "').addClass('bg-warning');";
      }else{
        echo "$('#th_" . $i. "').addClass('bg-success');";  
      }      
    } ?>
</script>