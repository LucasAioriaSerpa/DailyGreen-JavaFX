package org.dailygreen.dailygreen.Postagens.Evento;

import org.dailygreen.dailygreen.Postagens.Post.Post;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class Evento extends Post implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final long id_post = super.getSerialVersionUID();
    private long ID;
    private Date dataHoraInicio;
    private Date dataHoraFim;
    private String local;
    private String link;
    public Evento(int id_autor, String titulo, String descricao, Date dataHoraInicio, Date dataHoraFim, String local, String link) {
        super(id_autor, titulo, descricao);
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.local = local;
        this.link = link;
    }
    // ? GETTER AND SETTER
    public long getId_post() {return id_post;}
    public long getID() {return ID;}

    public Date getDataHoraInicio() {return dataHoraInicio;}
    public void setDataHoraInicio(Date dataHoraInicio) {this.dataHoraInicio = dataHoraInicio;}

    public Date getDataHoraFim() {return dataHoraFim;}
    public void setDataHoraFim(Date dataHoraFim) {this.dataHoraFim = dataHoraFim;}

    public String getLocal() {return local;}
    public void setLocal(String local) {this.local = local;}

    public String getLink() {return link;}
    public void setLink(String link) {this.link = link;}
}
