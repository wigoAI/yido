package org.moara.yido.db.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_ARA_SEN_GROUP_STRING")
public class TbAraSenGroupString {

    @Column(name = "CD_GROUP")
    private String cdGroup;

    @Id
    @Column(name = "VAL_STRING")
    private String valString;

    @Column(name = "DT_REG_FST")
    private LocalDateTime dtRegFst;
    @Column(name = "ID_EMP_REG")
    private String idEmpReg;
    @Column(name = "ID_DEPT_REG")
    private String idDeptReg;
    @Column(name = "DT_UPT_LAST")
    private String dtUptLast;
    @Column(name = "ID_EMP_UPT")
    private String idEmpUpt;
    @Column(name = "ID_DEPT_UPT")
    private String idDeptUpt;
    @Column(name = "FG_DEL")
    private String fgDel;

    public String getCdGroup() {
        return cdGroup;
    }

    public void setCdGroup(String cdGroup) {
        this.cdGroup = cdGroup;
    }

    public String getValString() {
        return valString;
    }

    public void setValString(String valString) {
        this.valString = valString;
    }

    public LocalDateTime getDtRegFst() {
        return dtRegFst;
    }

    public void setDtRegFst(LocalDateTime dtRegFst) {
        this.dtRegFst = dtRegFst;
    }

    public String getIdEmpReg() {
        return idEmpReg;
    }

    public void setIdEmpReg(String idEmpReg) {
        this.idEmpReg = idEmpReg;
    }

    public String getIdDeptReg() {
        return idDeptReg;
    }

    public void setIdDeptReg(String idDeptReg) {
        this.idDeptReg = idDeptReg;
    }

    public String getDtUptLast() {
        return dtUptLast;
    }

    public void setDtUptLast(String dtUptLast) {
        this.dtUptLast = dtUptLast;
    }

    public String getIdEmpUpt() {
        return idEmpUpt;
    }

    public void setIdEmpUpt(String idEmpUpt) {
        this.idEmpUpt = idEmpUpt;
    }

    public String getIdDeptUpt() {
        return idDeptUpt;
    }

    public void setIdDeptUpt(String idDeptUpt) {
        this.idDeptUpt = idDeptUpt;
    }

    public String getFgDel() {
        return fgDel;
    }

    public void setFgDel(String fgDel) {
        this.fgDel = fgDel;
    }
}
