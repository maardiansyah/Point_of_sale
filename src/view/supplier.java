/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import koneksi.koneksi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
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
public class supplier extends javax.swing.JFrame {
    Connection connection;
    DefaultTableModel model;
    /**
     * Creates new form barang
     */
    public supplier() {
        initComponents();
        connection = koneksi.koneksi();
        Tampil_Jam();
        Tampil_Tanggal();
        dataTable();
        refresh();
        getCmbBar();
        getCmbKat();
        tId.setVisible(false);
    }
    
    private void dataTable(){
        model = (DefaultTableModel) tbSup.getModel();
        try{
            Statement st = connection.createStatement();
            String sql = "select * from supplier, barang, kategori_barang "
                    + "where supplier.kategori_id = kategori_barang.kategori_id And supplier.barang_id = barang.barang_id "
                    + "order by supplier.Kd_Supplier ASC";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Object[] obj = new Object[7];
                obj[0] = rs.getString("Kd_Supplier");
                obj[1] = rs.getString("Nama_Supplier");
                obj[2] = rs.getString("Alamat");
                obj[5] = rs.getString("Telp");
                obj[3] = rs.getString("nama_kategori");
                obj[4] = rs.getString("nama_barang");
                obj[6] = rs.getString("id_supplier");
                model.addRow(obj);
            }
        }catch(SQLException er){
            er.printStackTrace();
        }
    }
    
    private void refresh(){
        model = (DefaultTableModel) tbSup.getModel();
        model.setRowCount(0);
        dataTable();
    }
    
    private void tambahData(){
            PreparedStatement stmnt = null;
            String query = "Insert Into supplier (Kd_Supplier, Nama_Supplier, Alamat, kategori_id, barang_id, Telp)" +
                    " values(?,?,?,?,?,?) ;";
            try{
                    stmnt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    stmnt.setInt(4, getIdKat(cbKat.getSelectedItem().toString()));
                    stmnt.setInt(5, getIdBar(cbBar.getSelectedItem().toString()));
                    stmnt.setString(1, tKode.getText());
                    stmnt.setString(2, tNama.getText());
                    stmnt.setString(3, tAlamat.getText());
                    stmnt.setString(6, tTelp.getText());
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
    private void cari(){
        model = (DefaultTableModel) tbSup.getModel();
        PreparedStatement stmnt = null;
        try{
        String query = "select* from supplier join barang on supplier.barang_id = barang.barang_id " +
                "join kategori_barang ON supplier.kategori_id = kategori_barang.kategori_id where Kd_Supplier Like ?" +
                "or Nama_Supplier Like ? Or barang.nama_barang like ? or kategori_barang.nama_kategori like ? or Alamat like ?" +
                "order by Nama_Supplier ASC";
                stmnt = connection.prepareStatement(query);
                stmnt.setString(1, "%" + tCari.getText() + "%");
                stmnt.setString(2, "%" + tCari.getText() + "%");
                stmnt.setString(3, "%" + tCari.getText() + "%");
                stmnt.setString(4, "%" + tCari.getText() + "%");
                stmnt.setString(5, "%" + tCari.getText() + "%");
                ResultSet rs = stmnt.executeQuery();
                while(rs.next()){
                Object[] obj = new Object[6];
                obj[0] = rs.getString("supplier.Kd_Supplier");
                obj[1] = rs.getString("supplier.Nama_Supplier");
                obj[2] = rs.getString("supplier.Alamat");
                obj[3] = rs.getString("kategori_barang.nama_kategori");
                obj[4] = rs.getString("barang.nama_barang");
                obj[5] = rs.getString("supplier.Telp");
                model.addRow(obj);
            }
        }catch (SQLException ex) {
                ex.printStackTrace();
        }
    } 
    
    private void reset(){
        tKode.setText("");
        bSimpan.setEnabled(true);
        tNama.setText("");
        tAlamat.setText("");
        tTelp.setText("");
    }
    
    private void update() {
        PreparedStatement stmnt = null;
        String query = "UPDATE `supplier` SET`Kd_Supplier`=?,`Nama_Supplier`=?,`Alamat`=?,`kategori_id`=?,`barang_id`=?,`Telp`=? " +
                "WHERE `id_supplier`=?";
        try{
                stmnt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmnt.setString(1, tKode.getText());
                stmnt.setString(2, tNama.getText()); 
                stmnt.setString(3, tAlamat.getText());
                stmnt.setInt(4, getIdKat(cbKat.getSelectedItem().toString()));
                stmnt.setInt(5, getIdBar(cbBar.getSelectedItem().toString()));
                stmnt.setString(6, tTelp.getText());
                stmnt.setString(7, tId.getText());   
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
    
    private void hapus(){
        PreparedStatement stmnt = null;
        String query = "Delete from supplier where id_supplier = ?";
        try{
                stmnt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmnt.setString(1, tId.getText().toString());
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
    
     private void getCmbKat() {
        cbKat.removeAllItems();
        try{
            Statement stat = connection.createStatement();
            String sql = "SELECT * FROM `kategori_barang`";
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()){
                cbKat.addItem(rs.getString("nama_kategori"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
     
      private int getIdKat(String nama_prodi) {
        int id = 0;
            try {
                PreparedStatement st = connection.prepareStatement("Select kategori_id from kategori_barang Where nama_kategori=?",Statement.RETURN_GENERATED_KEYS);
                st.setString(1, nama_prodi);
                ResultSet rs = st.executeQuery();
                while (rs.next()){
                    id = rs.getInt("kategori_id");
                } return id;
            } catch (SQLException ex) {
                Logger.getLogger(dataBarang.class.getName()).log(Level.SEVERE, null, ex);
            }return id;
    }
      
      private void getCmbBar() {
        cbKat.removeAllItems(); 
        //String nama = cbKat.getSelectedItem().toString();
            try{
            Statement stat = connection.createStatement();
            String sql = "SELECT * FROM barang " 
                    +" order by nama_barang asc";
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()){
                cbBar.addItem(rs.getString("nama_barang"));
            }
        }catch (SQLException e){
            e.printStackTrace();
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
                Logger.getLogger(supplier.class.getName()).log(Level.SEVERE, null, ex);
            }return id;
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
        date = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbSup = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        tCari = new javax.swing.JTextField();
        bCari = new javax.swing.JButton();
        bRefresh = new javax.swing.JButton();
        Tjam = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tKode = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tNama = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tAlamat = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        cbKat = new javax.swing.JComboBox<>();
        cbBar = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        bHapus = new javax.swing.JButton();
        bReset = new javax.swing.JButton();
        bUbah = new javax.swing.JButton();
        bSimpan = new javax.swing.JButton();
        tTelp = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tId = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Kategori Barang");
        setBackground(new java.awt.Color(102, 0, 102));

        jPanel1.setBackground(new java.awt.Color(102, 0, 102));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Imprint MT Shadow", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Data Supplier");

        date.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        date.setForeground(new java.awt.Color(255, 255, 255));
        date.setText("Date time");

        tbSup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Kode Supplier", "Nama Supplier", "Alamat", "Kategori", "Barang", "No. Telp", "Idl"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbSup.getTableHeader().setReorderingAllowed(false);
        tbSup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSupMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbSup);
        if (tbSup.getColumnModel().getColumnCount() > 0) {
            tbSup.getColumnModel().getColumn(0).setResizable(false);
            tbSup.getColumnModel().getColumn(0).setPreferredWidth(0);
            tbSup.getColumnModel().getColumn(1).setResizable(false);
            tbSup.getColumnModel().getColumn(1).setPreferredWidth(25);
            tbSup.getColumnModel().getColumn(6).setMinWidth(0);
            tbSup.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
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

        Tjam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Tjam.setForeground(new java.awt.Color(255, 255, 255));
        Tjam.setText("Time");

        jPanel2.setBackground(new java.awt.Color(204, 0, 153));

        jLabel3.setFont(new java.awt.Font("Imprint MT Shadow", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("FORM");

        jLabel9.setFont(new java.awt.Font("Imprint MT Shadow", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Kode Supplier");

        jLabel10.setFont(new java.awt.Font("Imprint MT Shadow", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Nama Supplier");

        jLabel11.setFont(new java.awt.Font("Imprint MT Shadow", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Alamat");

        tAlamat.setColumns(20);
        tAlamat.setRows(5);
        jScrollPane2.setViewportView(tAlamat);

        jLabel8.setFont(new java.awt.Font("Imprint MT Shadow", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Kategori Barang");

        cbKat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbBar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setFont(new java.awt.Font("Imprint MT Shadow", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Nama Barang");

        bHapus.setText("Hapus");
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });

        bReset.setText("Reset");
        bReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bResetActionPerformed(evt);
            }
        });

        bUbah.setText("Ubah");
        bUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUbahActionPerformed(evt);
            }
        });

        bSimpan.setText("Tambah");
        bSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bSimpanMouseClicked(evt);
            }
        });
        bSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSimpanActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Imprint MT Shadow", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("No. Telp");

        tId.setText("ID supplier");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(98, 98, 98))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbKat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbBar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addGap(45, 45, 45)
                                    .addComponent(tId))
                                .addComponent(tKode, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10)
                                .addComponent(tNama)
                                .addComponent(jLabel11)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
                            .addComponent(jLabel8)
                            .addComponent(jLabel12)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(bHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bSimpan))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(bUbah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bReset, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel13)
                            .addComponent(tTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(tId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbKat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bSimpan)
                    .addComponent(bUbah))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bHapus)
                    .addComponent(bReset))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
                            .addComponent(jSeparator3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tCari)
                                .addGap(18, 18, 18)
                                .addComponent(bCari, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Tjam)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(date)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator2)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(date)
                        .addComponent(Tjam)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bCari))
                        .addComponent(bRefresh))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel6)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void bCariMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bCariMouseEntered
        // TODO add your handling code here:
        
    }//GEN-LAST:event_bCariMouseEntered

    private void bRefreshMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bRefreshMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bRefreshMouseEntered

    private void bRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshActionPerformed
        // TODO add your handling code here:
        refresh();
    }//GEN-LAST:event_bRefreshActionPerformed

    private void bSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bSimpanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_bSimpanMouseClicked

    private void bSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSimpanActionPerformed
        // TODO add your handling code here:
        String nm = cbBar.getSelectedItem().toString(); 
        String stok = tNama.getText();
        if(!tKode.getText().isEmpty()){
            tambahData();
            refresh();
            reset();
            JOptionPane.showMessageDialog(this, "Supplier :  " + stok + "\ndengan Produk : " + nm +
                    "Berhasil ditambahkan",
                    "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this, "Gagal menambahkan supplier! \n Periksa Kembai data!",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bSimpanActionPerformed

    private void bUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbahActionPerformed
        // TODO add your handling code here:
        String nm = cbBar.getSelectedItem().toString(); 
        String stok = tNama.getText();
        if(!cbBar.getSelectedItem().toString().trim().isEmpty()){
            if(!tKode.getText().trim().isEmpty()){
                update();
                refresh();
                reset();
                JOptionPane.showMessageDialog(this, "Supplier Barang : " +nm+ " Berhasil dirubah",
                        "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Data belum lengkap!! \n Harap lengkapi terelebih dahulu",
                        "Notifikasi", JOptionPane.WARNING_MESSAGE);
            }
        }else {
             JOptionPane.showMessageDialog(this, "Pilih data yang ingin dirubah!",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bUbahActionPerformed

    private void bResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_bResetActionPerformed

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
        // TODO add your handling code here:
        String nm = cbBar.getSelectedItem().toString(); 
        String stok = tNama.getText();
        if(!cbBar.getSelectedItem().toString().trim().isEmpty()){
            int alert = JOptionPane.showConfirmDialog(this, "Apakah anda ingin menghapus supplier : "+ stok +
                    "\ndari menyuplai barang ini: " +nm+ " ?",
                    "Notifikasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(alert == JOptionPane.YES_NO_OPTION){
            hapus();
            refresh();
            reset();
            JOptionPane.showMessageDialog(this, "Data Supplier : " + nm + " Berhasil hapus",
                    "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Pilih data mahasiswa yang ingin dihapus",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bHapusActionPerformed

    private void bCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCariActionPerformed
        // TODO add your handling code here:
        if(!tCari.getText().trim().isEmpty()){
            model = (DefaultTableModel) tbSup.getModel();
            model.setRowCount(0);
            cari();
            tCari.setText("");
        }else {
            JOptionPane.showMessageDialog(this, "Masukan data yang ingin dicari",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bCariActionPerformed

    private void tbSupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSupMouseClicked
        // TODO add your handling code here
        tId.setText(tbSup.getModel().getValueAt(tbSup.getSelectedRow(), 6).toString());
        tKode.setText(tbSup.getModel().getValueAt(tbSup.getSelectedRow(), 0).toString());
        tNama.setText(tbSup.getModel().getValueAt(tbSup.getSelectedRow(), 1).toString());
        tAlamat.setText(tbSup.getModel().getValueAt(tbSup.getSelectedRow(), 2).toString());
        cbKat.setSelectedItem(tbSup.getModel().getValueAt(tbSup.getSelectedRow(), 3).toString());
        cbBar.setSelectedItem(tbSup.getModel().getValueAt(tbSup.getSelectedRow(), 4).toString());
        tTelp.setText(tbSup.getModel().getValueAt(tbSup.getSelectedRow(), 5).toString());
        
        bSimpan.setEnabled(false);
    }//GEN-LAST:event_tbSupMouseClicked

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
            java.util.logging.Logger.getLogger(supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new supplier().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Tjam;
    private javax.swing.JButton bCari;
    private javax.swing.JButton bHapus;
    private javax.swing.JButton bRefresh;
    private javax.swing.JButton bReset;
    private javax.swing.JButton bSimpan;
    private javax.swing.JButton bUbah;
    private javax.swing.JComboBox<String> cbBar;
    private javax.swing.JComboBox<String> cbKat;
    private javax.swing.JLabel date;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
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
    private javax.swing.JTextArea tAlamat;
    private javax.swing.JTextField tCari;
    private javax.swing.JLabel tId;
    private javax.swing.JTextField tKode;
    private javax.swing.JTextField tNama;
    private javax.swing.JTextField tTelp;
    private javax.swing.JTable tbSup;
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
