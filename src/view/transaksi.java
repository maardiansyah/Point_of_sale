/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author aldia
 */
public class transaksi extends javax.swing.JFrame {
    Connection connection;
    DefaultTableModel model;
    /**
     * Creates new form transaksi
     */
    public transaksi() {
        initComponents();
        connection = koneksi.koneksi();
        refresh();
        Lnama.setText("Barang yang di Cancel");
        refreshBelanja();
        tQty.requestFocus();
        total();
        jStok.setVisible(false);
        jStok1.setVisible(false);
        jStokId.setVisible(false);
        idTrans.setVisible(false);
        Tampil_Jam();
        Tampil_Tanggal();
    }
    
    private void refresh(){
        model = (DefaultTableModel) tbHarga.getModel();
        model.setRowCount(0);
        tableHarga();
    }
    
    private void tableHarga(){
        model = (DefaultTableModel) tbHarga.getModel();
        try{
            Statement st = connection.createStatement();
            String sql = "Select*From barang, kategori_barang, stok "
                    + "where barang.kategori_id = kategori_barang.kategori_id AND barang.barang_id=stok.barang_id "
                    + "order by barang.kategori_id ASC";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Object[] obj = new Object[5];
                obj[0] = rs.getString("barang_id");
                obj[1] = rs.getString("nama_kategori");
                obj[2] = rs.getString("nama_barang");
                obj[3] = rs.getString("harga");
                obj[4] = rs.getString("stok");
                model.addRow(obj);
            }
        }catch(SQLException er){
            er.printStackTrace();
        }
    }
    
    private void refreshBelanja(){
        model = (DefaultTableModel) tbBelanja.getModel();
        model.setRowCount(0);
        tableBelanja();
        model = (DefaultTableModel) tbHarga.getModel();
        model.setRowCount(0);
        tableHarga();
    }
    
        private void tableBelanja(){
        model = (DefaultTableModel) tbBelanja.getModel();
        try{
            Statement st = connection.createStatement();
            String sql = "select * from transaksi_detail, barang, stok where transaksi_detail.barang_id = barang.barang_id "
                    + "AND barang.barang_id = stok.barang_id AND transaksi_id = 0 Order by transaksi_detail.t_detail_id ASC";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Object[] obj = new Object[6];
                obj[0] = rs.getString("t_detail_id");
                obj[1] = rs.getString("nama_barang");
                obj[2] = rs.getString("harga");
                obj[3] = rs.getString("qty");
                obj[4] = rs.getString("sub_total");
                obj[5] = rs.getString("barang_id");
                model.addRow(obj);
            }
        }catch(SQLException er){
            er.printStackTrace();
        }
        Lid.setText("Hapus ");
        Lnama.setText("Nama Barang");
    }
        
        private void total(){
            try{
            Statement st = connection.createStatement();
            String sql = "SELECT SUM(sub_total)AS total FROM transaksi_detail where transaksi_id = 0";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                
                tTotal.setText(rs.getString("total"));
            }
        }catch(SQLException er){
            er.printStackTrace();
        }
     }
        
     private void cari(){
        model = (DefaultTableModel) tbHarga.getModel();
        PreparedStatement stmnt = null;
        try{
        String query = "Select*From barang JOIN kategori_barang ON barang.kategori_id = kategori_barang.kategori_id "
                + "Join stok ON barang.barang_id=stok.barang_id "
                + "where nama_barang Like ? or nama_kategori Like ?";
                stmnt = connection.prepareStatement(query);
                stmnt.setString(1, "%" + tCari.getText() + "%");
                stmnt.setString(2, "%" + tCari.getText() + "%");
                ResultSet rs = stmnt.executeQuery();
                while(rs.next()){
                 Object[] obj = new Object[5];
                obj[0] = rs.getString("barang_id");
                obj[2] = rs.getString("nama_barang");
                obj[3] = rs.getString("harga");
                obj[4] = rs.getString("stok");
                obj[1] = rs.getString("nama_kategori");
                model.addRow(obj);
            }
        }catch (SQLException ex) {
                ex.printStackTrace();
        }
    } 
     
     private void tambahBelanja(){
            PreparedStatement stmnt = null;
            int qty = Integer.parseInt(tQty.getText());
            int price = Integer.parseInt(tHarga.getText().toString());
            int subTotal = qty*price;
            int transaksi_id = 0;
            int status = 0;
            String query = "insert into transaksi_detail(barang_id, qty, harga, sub_total, transaksi_id, status) "
                    + "Values (?,?,?,?,?,?)";
            try{
                    stmnt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    stmnt.setInt(1, getIdBar(tNama.getText()));
                    stmnt.setInt(2, qty);
                    stmnt.setInt(3, price);
                    stmnt.setInt(4, subTotal);
                    stmnt.setInt(5, transaksi_id);
                    stmnt.setString(6, "0");
                    stmnt.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }finally{
                    try{stmnt.close();}
                    catch (SQLException ex){
                        ex.printStackTrace();
                    }
                }
        }
     
     private int getIdBar(String nama_barang) {
        int id = 0;
            try {
                PreparedStatement st = connection.prepareStatement("Select barang_id from barang Where nama_barang = ?",Statement.RETURN_GENERATED_KEYS);
                st.setString(1, nama_barang);
                ResultSet rs = st.executeQuery();
                while (rs.next()){
                    id = rs.getInt("barang_id");
                } return id;
            } catch (SQLException ex) {
                Logger.getLogger(transaksi.class.getName()).log(Level.SEVERE, null, ex);
            }return id;
    }
     
     private void hapusBelanja(){
         PreparedStatement stmnt = null;
        String query = "Delete from transaksi_detail where t_detail_id = ?";
        try{
                stmnt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmnt.setString(1, Lid.getText());
                stmnt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }finally{
                try{stmnt.close();}
                catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
     }
     
     private void kurangiStok(){
         PreparedStatement stmnt = null;
         int stok = Integer.parseInt(tQty.getText());
         String id = tKode.getText().toString();
         
         try {
        String sql = "Select stok from stok where barang_id = " + id;    
        Statement st = connection.createStatement();
        ResultSet res = st.executeQuery(sql);
        
        while (res.next()){
            jStok.setText(res.getString("stok"));
        }
    } catch (SQLException e){
        e.printStackTrace();}
         int kStok = Integer.parseInt(jStok.getText());
         int kurangStok = kStok-stok;
        String query = "update stok set stok = ? where barang_id = ?";
        try{
                stmnt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmnt.setInt(1, kurangStok);
                stmnt.setString(2, tKode.getText());
                stmnt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }finally{
                try{stmnt.close();}
                catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
     }
     
     private void tambahStok(){
         PreparedStatement stmnt = null;
         String id = jStok1.getText().toString();
         //mengambil jumlah stok dari belanjaan yang dihapus
         
         //mengambil data jumlah stok dari table stok berdasarkan id barang
         String sid = jStokId.getText().toString();
         try {
        String sQuery = "Select stok from stok where barang_id = " + sid;    
        Statement stm = connection.createStatement();
        ResultSet resu = stm.executeQuery(sQuery);
        
        while (resu.next()){
            jStok.setText(resu.getString("stok"));
        }
    } catch (SQLException e){
        e.printStackTrace();}
         
         int kStok = Integer.parseInt(jStok.getText());
         int stok = Integer.parseInt(jStok1.getText());
         int tambah = kStok+stok;
        String query = "update stok set stok = ? where barang_id = ?";
        try{
                stmnt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmnt.setInt(1, tambah);
                stmnt.setString(2, tKode.getText());
                stmnt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }finally{
                try{stmnt.close();}
                catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
     }
     private void selesai(){
         String total = tTotal.getText();
        //mengambil tanggal transaksi
        java.util.Date tglsekarang = new java.util.Date();
        SimpleDateFormat smpdtfmt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String tanggal = String.valueOf(smpdtfmt.format(tglsekarang));
        
        //menyelesaikan transaksi
        PreparedStatement stmnt = null;
        
        String query = "insert into transaksi(tanggal_transaksi, pemasukan) values (?,?)";
            try{
                    stmnt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    stmnt.setString(1, tanggal);
                    stmnt.setString(2, total);
                    stmnt.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }finally{
                    try{stmnt.close();}
                    catch (SQLException ex){
                        ex.printStackTrace();
                    }
                }
            
            try {
        String sQuery = "Select max(transaksi_id) as ids from transaksi";    
        Statement stm = connection.createStatement();
        ResultSet resu = stm.executeQuery(sQuery);
        
        while (resu.next()){
            idTrans.setText(resu.getString("ids"));
        }
    } catch (SQLException e){
        e.printStackTrace();}
            
     }
     private void updateTransaksiID (){
         PreparedStatement stmnt = null;  
         String ids = idTrans.getText();
         //update transaksi id pada table transaksi detail
         
        String sql = "update transaksi_detail set transaksi_id = ?, " 
                + "status = '1' where status = '0'";
        try{
                stmnt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                //stmnt.setString(1, SQ);
                stmnt.setString(1, ids);
                stmnt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }finally{
                try{stmnt.close();}
                catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        
        String query = "update transaksi_detail set status = '1' " 
                + "where transaksi_Id = ?";
        try{
                stmnt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                //stmnt.setString(1, SQ);
                stmnt.setString(1, ids);
                stmnt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }finally{
                try{stmnt.close();}
                catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
         
     }
     
     private void print(){
         try {
                boolean complete = tbBelanja.print();
                if (complete){
                    JOptionPane.showMessageDialog(null,"Sukses!!");
                } else {JOptionPane.showMessageDialog(null,"Eror!!");}
            }catch (PrinterException e) {
                JOptionPane.showMessageDialog(null, e);
           
            }
     }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        Tjam = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tKode = new javax.swing.JTextField();
        tNama = new javax.swing.JTextField();
        bSimpan = new javax.swing.JButton();
        bBelanja = new javax.swing.JButton();
        tQty = new javax.swing.JTextField();
        tHarga = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tCari = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        bCari = new javax.swing.JButton();
        bRefresh = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbHarga = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbBelanja = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tTotal = new javax.swing.JLabel();
        Lid = new javax.swing.JLabel();
        Lnama = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jStok = new javax.swing.JLabel();
        jStok1 = new javax.swing.JLabel();
        jStokId = new javax.swing.JLabel();
        idTrans = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 0, 102));

        jLabel1.setFont(new java.awt.Font("Imprint MT Shadow", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("TRANSAKSI");

        Tjam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Tjam.setForeground(new java.awt.Color(255, 255, 255));
        Tjam.setText("Time");

        date.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        date.setForeground(new java.awt.Color(255, 255, 255));
        date.setText("Date time");

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Perpetua Titling MT", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 0, 102));
        jLabel2.setText("Kode Barang");

        jLabel3.setFont(new java.awt.Font("Perpetua Titling MT", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 0, 102));
        jLabel3.setText("Nama Barang");

        jLabel4.setFont(new java.awt.Font("Perpetua Titling MT", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 0, 102));
        jLabel4.setText("QTY");

        tKode.setEditable(false);

        tNama.setEditable(false);

        bSimpan.setText("Simpan");
        bSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSimpanActionPerformed(evt);
            }
        });

        bBelanja.setText("Selesai");
        bBelanja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBelanjaActionPerformed(evt);
            }
        });

        tQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tQtyActionPerformed(evt);
            }
        });

        tHarga.setEditable(false);
        tHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tHargaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Perpetua Titling MT", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 0, 102));
        jLabel9.setText("harga");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(tNama, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(tKode, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel3)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(bSimpan)
                                .addGap(18, 18, 18)
                                .addComponent(bBelanja)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(tQty, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tHarga)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bBelanja)
                            .addComponent(bSimpan))
                        .addGap(22, 22, 22))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Pencarian");

        bCari.setBackground(new java.awt.Color(204, 204, 204));
        bCari.setForeground(new java.awt.Color(102, 0, 102));
        bCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/cari.png"))); // NOI18N
        bCari.setBorderPainted(false);
        bCari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bCariMouseEntered(evt);
            }
        });
        bCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCariActionPerformed(evt);
            }
        });

        bRefresh.setBackground(new java.awt.Color(204, 204, 204));
        bRefresh.setForeground(new java.awt.Color(102, 0, 102));
        bRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/refresh.png"))); // NOI18N
        bRefresh.setBorderPainted(false);
        bRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bRefreshMouseEntered(evt);
            }
        });
        bRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshActionPerformed(evt);
            }
        });

        tbHarga.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode Barang", "Kategori", "Barang", "Harga", "Stok"
            }
        ));
        tbHarga.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHargaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbHarga);
        if (tbHarga.getColumnModel().getColumnCount() > 0) {
            tbHarga.getColumnModel().getColumn(0).setMinWidth(0);
            tbHarga.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jPanel3.setBackground(new java.awt.Color(204, 0, 153));

        jLabel5.setFont(new java.awt.Font("Imprint MT Shadow", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Total");

        tbBelanja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID BELANJA", "Barang", "Harga", "Qty", "Sub Total", "ID Barang"
            }
        ));
        tbBelanja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBelanjaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbBelanja);
        if (tbBelanja.getColumnModel().getColumnCount() > 0) {
            tbBelanja.getColumnModel().getColumn(0).setMinWidth(0);
            tbBelanja.getColumnModel().getColumn(0).setMaxWidth(0);
            tbBelanja.getColumnModel().getColumn(5).setMinWidth(0);
            tbBelanja.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        jLabel7.setFont(new java.awt.Font("Imprint MT Shadow", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Daftar Belanja ");

        jLabel8.setFont(new java.awt.Font("Imprint MT Shadow", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Rp.");

        tTotal.setFont(new java.awt.Font("Imprint MT Shadow", 1, 18)); // NOI18N
        tTotal.setForeground(new java.awt.Color(255, 255, 255));
        tTotal.setText("000");

        Lid.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Lid.setForeground(new java.awt.Color(255, 255, 255));
        Lid.setText("Hapus");

        Lnama.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Lnama.setForeground(new java.awt.Color(255, 255, 255));
        Lnama.setText("Id belanjaan");

        jButton1.setText("Hapus");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText(" : ");

        jStok.setText("stok");

        jStok1.setText("jStok");

        jStokId.setText("Id_stok");

        idTrans.setText("IDS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 932, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(157, 157, 157)
                        .addComponent(tTotal)
                        .addGap(22, 22, 22))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(34, 34, 34)
                        .addComponent(jStok)
                        .addGap(33, 33, 33)
                        .addComponent(jStok1)
                        .addGap(18, 18, 18)
                        .addComponent(jStokId)
                        .addGap(26, 26, 26)
                        .addComponent(idTrans)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Lid)
                .addGap(3, 3, 3)
                .addComponent(jLabel11)
                .addGap(4, 4, 4)
                .addComponent(Lnama)
                .addGap(28, 28, 28)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Lid)
                            .addComponent(Lnama)
                            .addComponent(jButton1)
                            .addComponent(jLabel11)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jStok)
                            .addComponent(jStok1)
                            .addComponent(jStokId)
                            .addComponent(idTrans))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(tTotal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Tjam)
                .addGap(18, 18, 18)
                .addComponent(date)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tCari)
                                .addGap(18, 18, 18)
                                .addComponent(bCari, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1))))
                .addGap(45, 45, 45))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Tjam)
                        .addComponent(date))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bCari))
                                .addComponent(bRefresh))
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tQtyActionPerformed

    private void bCariMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bCariMouseEntered
        // TODO add your handling code here:

    }//GEN-LAST:event_bCariMouseEntered

    private void bCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCariActionPerformed
        // TODO add your handling code here:
        if(!tCari.getText().trim().isEmpty()){
            model = (DefaultTableModel) tbHarga.getModel();
            model.setRowCount(0);
            cari();
            tCari.setText("");
        }else {
            JOptionPane.showMessageDialog(this, "Masukan data yang ingin dicari",
                "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bCariActionPerformed

    private void bRefreshMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bRefreshMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bRefreshMouseEntered

    private void bRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshActionPerformed
        // TODO add your handling code here:
        refresh();
    }//GEN-LAST:event_bRefreshActionPerformed

    private void tHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tHargaActionPerformed

    private void tbHargaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHargaMouseClicked
        // TODO add your handling code here:
        tKode.setText(tbHarga.getModel().getValueAt(tbHarga.getSelectedRow(), 0).toString());
        tNama.setText(tbHarga.getModel().getValueAt(tbHarga.getSelectedRow(), 2).toString());
        tHarga.setText(tbHarga.getModel().getValueAt(tbHarga.getSelectedRow(), 3).toString());
        
    }//GEN-LAST:event_tbHargaMouseClicked

    private void bSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSimpanActionPerformed
        // TODO add your handling code here:
        if(!tKode.getText().isEmpty() && !tQty.getText().isEmpty()){
            tambahBelanja();
            refreshBelanja();
            total();
            kurangiStok();
            refresh();
            jStok.setText("");
            tQty.setText("");
//            JOptionPane.showMessageDialog(this, "",
//                        "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this, "Periksa kembali data belanjaan!",
                        "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
        }
        tQty.requestFocus();
        
    }//GEN-LAST:event_bSimpanActionPerformed

    private void tbBelanjaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBelanjaMouseClicked
        // TODO add your handling code here:
        Lid.setText(tbBelanja.getModel().getValueAt(tbBelanja.getSelectedRow(), 0).toString());
        Lnama.setText(tbBelanja.getModel().getValueAt(tbBelanja.getSelectedRow(), 1).toString());
        jStokId.setText(tbBelanja.getModel().getValueAt(tbBelanja.getSelectedRow(), 5).toString());
        jStok1.setText(tbBelanja.getModel().getValueAt(tbBelanja.getSelectedRow(), 3).toString());
    }//GEN-LAST:event_tbBelanjaMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here
        if(Lid.getText() == "Hapus"){
            JOptionPane.showMessageDialog(this, "Pilih daftar belanja yang ingin dihapus!",
                    "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
        } else {
            hapusBelanja();
            refreshBelanja();
            total();
            refresh();
            tambahStok();
            Lid.setText("Hapus");
            tQty.setText("");
            tTotal.setText("000");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void bBelanjaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBelanjaActionPerformed
        // TODO add your handling code here:
        selesai();
        updateTransaksiID();
        Lid.setText("Hapus");
        tQty.setText("");
        refresh();
        refreshBelanja();
        idTrans.setText("");
        tTotal.setText("000");
    }//GEN-LAST:event_bBelanjaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JLabel Lid;
    private javax.swing.JLabel Lnama;
    private javax.swing.JLabel Tjam;
    private javax.swing.JButton bBelanja;
    private javax.swing.JButton bCari;
    private javax.swing.JButton bRefresh;
    private javax.swing.JButton bSimpan;
    private javax.swing.JLabel date;
    private javax.swing.JLabel idTrans;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel jStok;
    private javax.swing.JLabel jStok1;
    private javax.swing.JLabel jStokId;
    private javax.swing.JTextField tCari;
    private javax.swing.JTextField tHarga;
    private javax.swing.JTextField tKode;
    private javax.swing.JTextField tNama;
    private javax.swing.JTextField tQty;
    private javax.swing.JLabel tTotal;
    private javax.swing.JTable tbBelanja;
    private javax.swing.JTable tbHarga;
    // End of variables declaration//GEN-END:variables

public void Tampil_Jam(){
        ActionListener taskPerformer = new ActionListener() {
 
        @Override
            public void actionPerformed(ActionEvent evt) {
            String nol_jam = "", nol_menit = "",nol_detik = "";
 
            java.util.Date dateTime = new java.util.Date();
            int nilai_jam = dateTime.getHours();
            int nilai_menit = dateTime.getMinutes();
            int nilai_detik = dateTime.getSeconds();
 
            if(nilai_jam <= 9) nol_jam= "0";
            if(nilai_menit <= 9) nol_menit= "0";
            if(nilai_detik <= 9) nol_detik= "0";
 
            String jam = nol_jam + Integer.toString(nilai_jam);
            String menit = nol_menit + Integer.toString(nilai_menit);
            String detik = nol_detik + Integer.toString(nilai_detik);
 
            Tjam.setText(jam+":"+menit+":"+detik+"");
            }
        };
    new Timer(1000, taskPerformer).start();
    }   
 
    public void Tampil_Tanggal() {
    java.util.Date tglsekarang = new java.util.Date();
    SimpleDateFormat smpdtfmt = new SimpleDateFormat("dd MMMMMMMMM yyyy", Locale.getDefault());
    String tanggal = smpdtfmt.format(tglsekarang);
    date.setText(tanggal);
    }

}
