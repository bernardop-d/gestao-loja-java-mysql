package com.bernardo.loja.model;

import java.time.LocalDateTime;

public class Venda {
  private int id; private Integer clienteId; private int usuarioId; private LocalDateTime dataVenda;
  public int getId(){return id;} public void setId(int id){this.id=id;}
  public Integer getClienteId(){return clienteId;} public void setClienteId(Integer clienteId){this.clienteId=clienteId;}
  public int getUsuarioId(){return usuarioId;} public void setUsuarioId(int usuarioId){this.usuarioId=usuarioId;}
  public LocalDateTime getDataVenda(){return dataVenda;} public void setDataVenda(LocalDateTime dataVenda){this.dataVenda=dataVenda;}
}
