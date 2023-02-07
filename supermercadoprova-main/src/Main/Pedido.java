package Main;

import Utils.Inputs;

import java.util.ArrayList;
import java.util.Scanner;

public class Pedido {

    private static ArrayList<Item> listaDeItens = new ArrayList();
    private static double valorTotalDoPedido = 0;

    public static void calculaValorTotal() {
        double subTotal = 0;
        for (Item item : listaDeItens) {
            subTotal += item.getValorDoItem();
        }
        valorTotalDoPedido = subTotal;
    }

    //criar metodo para receber o valor pago e calcular o troco (funcionalidade)
    public static void ValorPago() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Valor a ser pago: " + valorTotalDoPedido);
        
        if(valorTotalDoPedido == 0) {
        	System.out.println("Carrinho vazio! Nada a pagar");
        	return;
        }
        System.out.println("Informe o valor para pagar: ");
        double valor = sc.nextDouble();  
        Double troco = valor - valorTotalDoPedido;;
        
        
       if(valor >= valorTotalDoPedido) {
            System.out.println("Valor do troco: " + troco);
        }else {
            System.out.println("Valor informado menor do que o pedido!");
        }      
        
        //chamando o metodo para contar as notas
        menorNotas(troco);
        
        //Limpando o carrinho após o pagamento e Zerando valor total
        limparCarrinho();
        valorTotalDoPedido = 0;
        
        //sc.close();
    }
    
    //criar metodo que defina a menor quantidade de notas possivel para o troco (funcionalidade dificil)
    //Notas possiveis = 100,50,20,10,5,2,1,0.5,0.25,0.1,0.05 e 0.01;
    
    
    //Fiz 'n' contador para saber a quantidade de notas, 
    //sempre a acabar o if eu zero 'n' para fazer a nova contagem das cedulas
    
    public static void menorNotas(Double troco) {
    	int n = 0;
        if(troco >= 100 ) {
            while(troco >= 100) { n++; troco +=(double) - 100; }
            	System.out.println("Notas de 100: " + (n++));
        }
        if(troco >= 50) {
        	n = 0;
        	while(troco >= 50 ){n++; troco +=(double) - 50; }
        	System.out.println("Notas de 50: " + (n++));
        }
        if(troco >= 20) {
        	n = 0;
        	while(troco >= 20 ){n++; troco +=(double) - 20; }
        	System.out.println("Notas de 20: " + (n++));
        }
        if(troco >= 10) {
        	n = 0;
        	while(troco >= 10 ){n++; troco +=(double) - 10; }
        	System.out.println("Notas de 10: " + (n++));
        }
        if(troco >= 5) {
        	n = 0;
        	while(troco >= 5 ){n++; troco +=(double) - 5; }
        	System.out.println("Notas de 5: " + (n++));
        }
        if(troco >= 2) {
        	n = 0;
        	while(troco >= 2 ){n++; troco +=(double) - 2; }
        	System.out.println("Notas de 2: " + (n++));
        }
        if(troco >= 1) {
        	n = 0;
        	while(troco >= 1 ){n++; troco +=(double) - 1; }
        	System.out.println("Notas de 1: " + (n++));
        }
        if(troco >= 0.50) {
        	n = 0;
        	while(troco >= 0.50 ){n++; troco +=(double) - 0.50; }
        	System.out.println("Notas de 0.50: " + (n++));
        }
        if(troco >= 0.25) {
        	n = 0;
        	while(troco >= 0.25 ){n++; troco +=(double) - 0.25; }
        	System.out.println("Notas de 0.25: " + (n++));
        }
        if(troco >= 0.1) {
        	n = 0;
        	while(troco >= 0.1 ){n++; troco +=(double) - 0.1; }
        	System.out.println("Notas de 0.10: " + (n++));
        }
        if(troco >= 0.05) {
        	n = 0;
        	while(troco >= 0.05 ){n++; troco +=(double) - 0.05; }
        	System.out.println("Notas de 0.05: " + (n++));
        }
        
        if(troco >= 0.01) {
        	n = 0;
        	while(troco >= 0.01 ){n++; troco +=(double) - 0.01; }
        	System.out.println("Notas de 0.01: " + (n++));
        }

    }
    
    
    public static boolean adicionaItemNaLista(Produto produto, int quantidade) {
        for (Item item : listaDeItens) {
            if (item.getProduto().getNome().equalsIgnoreCase(produto.getNome())) {
                Estoque.darBaixaEmEstoque(item.getProduto().getId(), quantidade);
                //item.setQuantidade(item.getQuantidade() + quantidade);
                item.defineValorTotal();
                //System.out.println("Foi adicionada a quantidade ao item já existente.");
                return false;
            }
        }
        listaDeItens.add(new Item(produto, quantidade));
        Estoque.darBaixaEmEstoque(produto.getId(), quantidade);
        System.out.println("Foi adicionado o produto na lista de compras.");
        return true;
    }

    public static void imprimePedido() {
        System.out.println("                              NOTA FISCAL");
        System.out.printf("ID       |NOME            |PRECO UN           |QUANTIDADE   |PRECO ITEM \n");
        for (Item item : listaDeItens) {
            System.out.printf("%-8d | %-14s | R$%-15.2f | %-10d  | R$%.2f\n"
                    , item.getProduto().getId(), item.getProduto().getNome(), item.getProduto().getPreco(), item.getQuantidade(), item.getValorDoItem());
        }
        imprimeValorTotal();
    }

    private static void imprimeValorTotal() {
        System.out.println();
        System.out.printf("Total: R$%.2f", valorTotalDoPedido);
        System.out.println("________________________________________________________________________");
        System.out.println();
        System.out.println();
    }

    public static void adicionaItem(){
        String nome = recebeNomeDoTeclado();
        int quantidade = recebeQuantidadeDoTeclado();
        Produto produto = Estoque.encontraProduto(nome);
        if(produto != null){
            adicionaItemNaLista(produto,quantidade);
            calculaValorTotal();
        } else {
            System.out.println("Produto nao encontrado");
        }

    }

    public static String recebeNomeDoTeclado(){
        System.out.print("Digite o nome: ");
        return Inputs.inputString();
    }


    public static int recebeQuantidadeDoTeclado(){
        System.out.print("Digite a quantidade: ");
        return Inputs.inputInt();
    }

    public static void limparCarrinho() {
        listaDeItens.clear();
    }

    public static ArrayList<Item> getListaDeItens() {
        return listaDeItens;
    }

    public void setListaDeItens(ArrayList<Item> listaDeItens) {
        Pedido.listaDeItens = listaDeItens;
    }

    public double getValorTotalDoPedido() {
        return valorTotalDoPedido;
    }

    public void setValorTotalDoPedido(double valorTotalDoPedido) {
        Pedido.valorTotalDoPedido = valorTotalDoPedido;
    }
}
