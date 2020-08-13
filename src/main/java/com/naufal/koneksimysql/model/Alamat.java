package com.naufal.koneksimysql.model;

public class Alamat {

	private long id;
    private String nama_jalan;
    private String RT;
    private String RW;
    private String kode_desa;
    private String kode_kec;
    private String kode_kab;
    private String kode_prop;

    public Alamat(String nama_jalan, String RT, String RW, String kode_desa, String kode_kec, String kode_kab, String kode_prop) {
        this.nama_jalan = nama_jalan;
        this.RT = RT;
        this.RW = RW;
        this.kode_desa = kode_desa;
        this.kode_kec = kode_kec;
        this.kode_kab = kode_kab;
        this.kode_prop = kode_prop;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNama_jalan() {
        return nama_jalan;
    }

    public void setNama_jalan(String nama_jalan) {
        this.nama_jalan = nama_jalan;
    }

    public String getRT() {
        return RT;
    }

    public void setRT(String RT) {
        this.RT = RT;
    }

    public String getRW() {
        return RW;
    }

    public void setRW(String RW) {
        this.RW = RW;
    }

    public String getKode_desa() {
        return kode_desa;
    }

    public void setKode_desa(String kode_desa) {
        this.kode_desa = kode_desa;
    }

    public String getKode_kec() {
        return kode_kec;
    }

    public void setKode_kec(String kode_kec) {
        this.kode_kec = kode_kec;
    }

    public String getKode_kab() {
        return kode_kab;
    }

    public void setKode_kab(String kode_kab) {
        this.kode_kab = kode_kab;
    }

    public String getKode_prop() {
        return kode_prop;
    }

    public void setKode_prop(String kode_prop) {
        this.kode_prop = kode_prop;
    }

    @Override
    public String toString() {
        return "Alamat{" + "id=" + id + ", nama_jalan=" + nama_jalan + ", RT=" + RT + ", RW=" + RW + ", kode_desa=" + kode_desa + ", kode_kec=" + kode_kec + ", kode_kab=" + kode_kab + ", kode_prop=" + kode_prop + '}';
    }
	
}
