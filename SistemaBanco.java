import java.util.ArrayList;


class ContaBancaria {
    private String numeroConta;
    private double saldo;
    private String titular;

    public ContaBancaria(String numeroConta, String titular) {
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.saldo = 0.0;
    }

    
    public String getNumeroConta() {
        return numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getTitular() {
        return titular;
    }

    
    protected void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    
    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.println("Depósito de R$ " + valor + " realizado na conta " + numeroConta);
        } else {
            System.out.println("Valor de depósito inválido.");
        }
    }

    
    public boolean sacar(double valor) {
        if (valor > 0 && saldo >= valor) {
            saldo -= valor;
            System.out.println("Saque de R$ " + valor + " realizado na conta " + numeroConta);
            return true;
        } else {
            System.out.println("Saldo insuficiente ou valor inválido para saque na conta " + numeroConta);
            return false;
        }
    }

    @Override
    public String toString() {
        return "Conta: " + numeroConta + " | Titular: " + titular + " | Saldo: R$ " + String.format("%.2f", saldo);
    }
}


class ContaCorrente extends ContaBancaria {
    private double taxaManutencao;

    public ContaCorrente(String numeroConta, String titular, double taxaManutencao) {
        super(numeroConta, titular);
        this.taxaManutencao = taxaManutencao;
    }

    
    @Override
    public boolean sacar(double valor) {
        double valorTotal = valor + taxaManutencao;
        if (valor > 0 && getSaldo() >= valorTotal) {
            setSaldo(getSaldo() - valorTotal);
            System.out.println("Saque de R$ " + valor + " com taxa de manutenção R$ " + taxaManutencao + " realizado na conta " + getNumeroConta());
            return true;
        } else {
            System.out.println("Saldo insuficiente para saque + taxa na conta " + getNumeroConta());
            return false;
        }
    }
}


class ContaPoupanca extends ContaBancaria {
    private double taxaRendimento; 

    public ContaPoupanca(String numeroConta, String titular, double taxaRendimento) {
        super(numeroConta, titular);
        this.taxaRendimento = taxaRendimento;
    }

    public void aplicarRendimento() {
        double rendimento = getSaldo() * taxaRendimento;
        setSaldo(getSaldo() + rendimento);
        System.out.println("Rendimento de R$ " + String.format("%.2f", rendimento) + " aplicado na conta " + getNumeroConta());
    }
}

class Banco {
    private ArrayList<ContaBancaria> contas;

    public Banco() {
        contas = new ArrayList<>();
    }

    public void abrirConta(ContaBancaria novaConta) {
        contas.add(novaConta);
        System.out.println("Conta aberta: " + novaConta.getNumeroConta() + " - Titular: " + novaConta.getTitular());
    }

    public ContaBancaria buscarConta(String numero) {
        for (ContaBancaria conta : contas) {
            if (conta.getNumeroConta().equals(numero)) {
                return conta;
            }
        }
        return null;
    }

    public void realizarOperacao(String numeroConta, String tipoOperacao, double valor) {
        ContaBancaria conta = buscarConta(numeroConta);
        if (conta == null) {
            System.out.println("Conta não encontrada: " + numeroConta);
            return;
        }

        switch (tipoOperacao.toLowerCase()) {
            case "depositar":
                conta.depositar(valor);
                break;
            case "sacar":
                conta.sacar(valor);
                break;
            default:
                System.out.println("Operação inválida: " + tipoOperacao);
        }
    }

    public void listarContas() {
        System.out.println("\n----- Lista de Contas -----");
        for (ContaBancaria conta : contas) {
            System.out.println(conta);
        }
        System.out.println("---------------------------");
    }
}


public class SistemaBanco {
    public static void main(String[] args) {
        Banco banco = new Banco();

        
        ContaCorrente cc1 = new ContaCorrente("001", "João Silva", 2.5);
        ContaPoupanca cp1 = new ContaPoupanca("002", "Maria Oliveira", 0.01); // 1% rendimento

        banco.abrirConta(cc1);
        banco.abrirConta(cp1);

        
        banco.realizarOperacao("001", "depositar", 1000);
        banco.realizarOperacao("002", "depositar", 2000);

       
        banco.realizarOperacao("001", "sacar", 100);  
        banco.realizarOperacao("002", "sacar", 50);   

        
        ContaBancaria contaPoupanca = banco.buscarConta("002");
        if (contaPoupanca instanceof ContaPoupanca) {
            ((ContaPoupanca) contaPoupanca).aplicarRendimento();
        }


        banco.listarContas();
    }
}
