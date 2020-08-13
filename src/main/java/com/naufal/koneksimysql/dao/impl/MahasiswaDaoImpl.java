package com.naufal.koneksimysql.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.naufal.koneksimysql.dao.MahasiswaDao;
import com.naufal.koneksimysql.model.Mahasiswa;

public class MahasiswaDaoImpl implements MahasiswaDao {
	
	private final Connection connection;
	
	private final String INSERT = "INSERT INTO mahasiswa (nim, nama, ipk, jurusan) "
            + "	VALUES (?,?,?,?)";
    private final String UPDATE = "UPDATE mahasiswa SET nama=?, ipk=?, jurusan=? WHERE nim=?";
    private final String DELETE = "DELETE FROM mahasiswa WHERE nim=?";
    private final String SELECT_ALL = "SELECT nim,nama,ipk,jurusan FROM mahasiswa";
    private final String SELECT_BY_NIM = "SELECT nim,nama,ipk,jurusan FROM mahasiswa WHERE nim=?";

    private final String INSERT_ALAMAT = "INSERT INTO alamat(nim,nama_jalan,rt,rw,kode_desa,kode_kec,kode_kab,kode_prop) VALUES(?,?,?,?,?,?,?,?)";
    private final String UPDATE_ALAMAT = "UPDATE alamat SET nama_jalan=?, rt=?, rw=?,kode_desa=?,kode_kec=?,kode_kab=?,kode_prop=? WHERE nim=?";
    
    public MahasiswaDaoImpl(Connection connection) {
        this.connection = connection;
    }

	@Override
	public boolean insert(Mahasiswa m) {
		
		PreparedStatement prepareStatementMhs = null;
		PreparedStatement prepareStatementAlamat = null;
		
		try {
			
			connection.setAutoCommit(false);
			
			prepareStatementMhs = connection.prepareStatement(INSERT);
			prepareStatementMhs.setString(1, m.getNim());
			prepareStatementMhs.setString(2, m.getNama());
            prepareStatementMhs.setFloat(3, m.getIpk());
            prepareStatementMhs.setString(4, m.getJurusan());
            
            boolean isInserted = prepareStatementMhs.executeUpdate() > 0;
            
            prepareStatementAlamat = connection.prepareStatement(INSERT_ALAMAT);
            prepareStatementAlamat.setString(1, m.getNim());
            prepareStatementAlamat.setString(2, m.getAlamat().getNama_jalan());
            prepareStatementAlamat.setString(3, m.getAlamat().getRT());
            prepareStatementAlamat.setString(4, m.getAlamat().getRW());
            prepareStatementAlamat.setString(5, m.getAlamat().getKode_desa());
            prepareStatementAlamat.setString(6, m.getAlamat().getKode_kec());
            prepareStatementAlamat.setString(7, m.getAlamat().getKode_kab());
            prepareStatementAlamat.setString(8, m.getAlamat().getKode_prop());
            boolean isInsertedAlamat = prepareStatementAlamat.executeUpdate() > 0;
            if (isInserted && isInsertedAlamat) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
            }
            
		}catch (SQLException ex) {
            
			Logger.getLogger(MahasiswaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(MahasiswaDaoImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
			
        } finally {
            
        	try {
                connection.setAutoCommit(true);
                if (prepareStatementMhs != null) {
                    prepareStatementMhs.close();
                }
                if (prepareStatementAlamat != null) {
                    prepareStatementAlamat.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MahasiswaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        	
        }
		return false;
	}

	@Override
	public boolean update(Mahasiswa m) {
		
		PreparedStatement prepareStatementMhs = null;
        PreparedStatement prepareStatementAlmt = null;
        try {
            connection.setAutoCommit(false);
            prepareStatementMhs = connection.prepareStatement(UPDATE);
            prepareStatementMhs.setString(1, m.getNama());
            prepareStatementMhs.setFloat(2, m.getIpk());
            prepareStatementMhs.setString(3, m.getJurusan());
            prepareStatementMhs.setString(4, m.getNim());
            boolean updatedMhs = prepareStatementMhs.executeUpdate() > 0;

            prepareStatementAlmt = connection.prepareStatement(UPDATE_ALAMAT);
            prepareStatementAlmt.setString(1, m.getAlamat().getNama_jalan());
            prepareStatementAlmt.setString(2, m.getAlamat().getRT());
            prepareStatementAlmt.setString(3, m.getAlamat().getRW());
            prepareStatementAlmt.setString(4, m.getAlamat().getKode_desa());
            prepareStatementAlmt.setString(5, m.getAlamat().getKode_kec());
            prepareStatementAlmt.setString(6, m.getAlamat().getKode_kab());
            prepareStatementAlmt.setString(7, m.getAlamat().getKode_prop());
            prepareStatementAlmt.setString(8, m.getNim());
            boolean updatedAlmt = prepareStatementMhs.executeUpdate() > 0;
            if (updatedMhs && updatedAlmt) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MahasiswaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(MahasiswaDaoImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                if (prepareStatementMhs != null) {
                    prepareStatementMhs.close();
                }
                if (prepareStatementAlmt != null) {
                    prepareStatementAlmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MahasiswaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
	}

	@Override
	public boolean delete(String nim) {
		
		PreparedStatement prepareStatement = null;
        try {
            prepareStatement = connection.prepareStatement(DELETE);
            prepareStatement.setString(1, nim);
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(MahasiswaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (prepareStatement != null) {
                try {
                    prepareStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MahasiswaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
		
	}

	@Override
	public Mahasiswa getMahasiswaByNim(String nim) {
		
		PreparedStatement prepareStatement = null;
		ResultSet executeQuery = null;
		Mahasiswa m = null;
		
		try {
			prepareStatement = connection.prepareStatement(SELECT_BY_NIM);
			prepareStatement.setString(1, nim);
			executeQuery = prepareStatement.executeQuery();
			if(executeQuery.next()) {
				System.out.println(""+SELECT_BY_NIM);
                m = new Mahasiswa(executeQuery.getNString("nim"), 
                		executeQuery.getString("nama"), 
                		executeQuery.getFloat("ipk"), 
                		executeQuery.getString("jurusan"));
                
			}
		}catch (SQLException ex) {
            Logger.getLogger(MahasiswaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        	
            try {
                if (prepareStatement != null) {
                    prepareStatement.close();
                }
                if (executeQuery != null) {
                    executeQuery.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MahasiswaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return m;
		
	}

	@Override
	public List<Mahasiswa> getAllMahasiswa() {
		
		List<Mahasiswa> mahasiswas = new ArrayList<>();
        Statement statement = null;
        ResultSet executeQuery = null;
        try {
            statement = connection.createStatement();
            executeQuery = statement.executeQuery(SELECT_ALL);
            while (executeQuery.next()) {
                Mahasiswa m = new Mahasiswa(executeQuery.getNString("nim"), 
                		executeQuery.getString("nama"), 
                		executeQuery.getFloat("ipk"), 
                		executeQuery.getString("jurusan"));
                mahasiswas.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MahasiswaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                if (statement != null) {
                    statement.close();
                }
                if (executeQuery != null) {
                    executeQuery.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MahasiswaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return mahasiswas;
		
	}

}
