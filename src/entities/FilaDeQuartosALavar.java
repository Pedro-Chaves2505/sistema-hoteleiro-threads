package entities;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.PriorityBlockingQueue; 


public class FilaDeQuartosALavar {
  private ArrayList<Quarto> listaDeQuartosALavar = new ArrayList<Quarto>();
  private ReentrantLock lock = new ReentrantLock();
  private Condition condicaoFilaVazia = lock.newCondition();

  //   public FilaDeQuartosALavar(ArrayList<Quarto> listaDeQuartosALavar) {
//     this.listaDeQuartosALavar = listaDeQuartosALavar;
//   }
    public FilaDeQuartosALavar(){
      this.listaDeQuartosALavar = new ArrayList<>(); 
    }
    
    public boolean contains(Quarto quarto){
      return this.listaDeQuartosALavar.contains(quarto);
    }
    public Quarto pop() {
      this.lock.lock();
      while(this.size() ==0){
        try{
          condicaoFilaVazia.await();
        } catch (InterruptedException e){
          System.out.println("O que coloca aqui?");
        }
      }
      Quarto quartoALavar = this.listaDeQuartosALavar.remove(0);
      System.out.println("Chave do quarto"+ quartoALavar.getNumero() +" retirada da fila de limpeza");
      System.out.println("[POP]" +this.toString());
      this.lock.unlock();
      return quartoALavar;

    
  }

  public void push(Quarto quarto) {
    this.lock.lock();
    this.listaDeQuartosALavar.add(quarto);
    condicaoFilaVazia.signalAll();
    System.out.println("Chave do quarto " + quarto.getNumero() + " entregue para limpeza");
    System.out.println("[PUSH]" + this.toString());
    this.lock.unlock();
    return;
  }
  public Quarto peek(){
    System.out.println("[PEEK]" + this.toString());
    return this.listaDeQuartosALavar.get(0);
  }
  
  public int size(){
    return this.listaDeQuartosALavar.size();
  }


  public String toString(){
    String stringFilaDeQuartosALavar = "FILA DE QUARTOS A LAVAR\n";
    if(this.size() == 0){
        return stringFilaDeQuartosALavar + "--Não há quartos a lavar--";
    }
    int i = 1;
    for (Quarto quarto : listaDeQuartosALavar) {
        if(i == this.size()){
            stringFilaDeQuartosALavar += quarto.getNumero().toString() + ".";
        }
        else{
            stringFilaDeQuartosALavar += quarto.getNumero().toString() + ",";
        }
        i++;
    }
    return stringFilaDeQuartosALavar;
  } 


}