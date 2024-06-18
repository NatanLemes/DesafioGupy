// import static org.junit.jupiter.api.Assertions.assertEquals;

// import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.math.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Main {

        // funcao para formatar o salario no padrao solicitado
        public static String formatarNumero(BigDecimal numero) {
                DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.getDefault());
                formatSymbols.setDecimalSeparator(',');
                formatSymbols.setGroupingSeparator('.');

                String pattern = "#,##0.00";
                DecimalFormat formatter = new DecimalFormat(pattern, formatSymbols);

                return formatter.format(numero);
        }

        public static void main(String[] args) {

                // 3.1 - Criação da lista de funcionários

                List<Funcionario> funcionarios = new ArrayList<Funcionario>();
                funcionarios.add(new Funcionario("Maria", LocalDate.of(1985, 10, 18), new BigDecimal("2009.44"),
                                "Operador"));
                funcionarios.add(new Funcionario("João", LocalDate.of(1990, 05, 12), new BigDecimal("2284.38"),
                                "Operador"));
                funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 05, 02), new BigDecimal("9836.14"),
                                "Coordenador"));
                funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"),
                                "Diretor"));
                funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 01, 05), new BigDecimal("2234.68"),
                                "Recepcionista"));
                funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"),
                                "Operador"));
                funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 03, 31), new BigDecimal("4071.84"),
                                "Contador"));
                funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 07, 8), new BigDecimal("3017.45"),
                                "Gerente"));
                funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 05, 24), new BigDecimal("1606.85"),
                                "Eletricista"));
                funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 02), new BigDecimal("2799.93"),
                                "Gerente"));

                // 3.2 - Remover o funcionário "João" da lista
                funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

                // 3.3 - Imprimir todos os funcionários com informações formatadas
                System.out.println("Funcionários:");
                for (Funcionario funcionario : funcionarios) {
                        System.out.println("Nome: " + funcionario.getNome());
                        System.out.println("Função: " + funcionario.getFuncao());
                        System.out.println("Data de Nascimento: "
                                        + funcionario.getDataNascimento()
                                                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        System.out.println("Salário: R$"
                                        + formatarNumero(funcionario.getSalario()));
                        System.out.println("--------------------");
                } // System.out.println("Salário: R$" + String.format("%,.2f",
                  // funcionario.getSalario()).replace('.', ','));

                // 3.4 - Aumento de 10% no salário
                funcionarios.forEach(funcionario -> funcionario
                                .setSalario(funcionario.getSalario().multiply(new BigDecimal("1.10"))));

                // 3.5 - Agrupar funcionários por função
                Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                                .collect(Collectors.groupingBy(Funcionario::getFuncao));

                // 3.6 - Imprimir funcionários agrupados por função
                System.out.println("Funcionários por Função:");
                funcionariosPorFuncao.forEach((funcao, listaFuncionarios) -> {
                        System.out.println("Função: " + funcao);
                        listaFuncionarios.forEach(funcionario -> System.out.println(" -> " +
                                        funcionario.getNome()));
                        System.out.println("--------------------");
                });

                // 3.8 - Imprimir funcionários que fazem aniversário em outubro e dezembro
                System.out.println("Funcionários que fazem aniversário em outubro e dezembro:");
                funcionarios.stream()
                                .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == 10
                                                || funcionario.getDataNascimento().getMonthValue() == 12)
                                .forEach(funcionario -> System.out.println("- " + funcionario.getNome()));
                System.out.println("--------------------");
                // 3.9 - Imprimir funcionário com maior idade
                Funcionario funcionarioMaisVelho = funcionarios.stream()
                                .max(Comparator.comparingInt(funcionario -> Period
                                                .between(funcionario.getDataNascimento(), LocalDate.now()).getYears()))
                                .orElse(null);
                if (funcionarioMaisVelho != null) {
                        System.out.println("Funcionário mais velho:");
                        System.out.println("Nome: " + funcionarioMaisVelho.getNome());
                        System.out.println(
                                        "Idade: " + Period.between(funcionarioMaisVelho.getDataNascimento(),
                                                        LocalDate.now()).getYears());
                }
                System.out.println("--------------------");
                // 3.10 - Imprimir lista de funcionários por ordem alfabética
                System.out.println("Funcionários por ordem alfabética:");
                funcionarios.stream().sorted(Comparator.comparing(Funcionario::getNome))
                                .forEach(funcionario -> System.out.println("->" + funcionario.getNome()));

                // 3.11 - Imprimir total dos salários
                // BigDecimal totalSalarios = funcionarios.stream()
                // .mapToDouble(Funcionario::getSalario)
                // .sum();

                // BigDecimal totalSalarios = funcionarios.stream()
                // .map(Funcionario::getSalario)
                // .reduce(BigDecimal.ZERO, BigDecimal::add);
                System.out.println("--------------------");
                BigDecimal totalSalario = BigDecimal.ZERO;

                for (Funcionario funcionario : funcionarios) {
                        totalSalario = totalSalario.add(funcionario.getSalario());
                        // System.out.println(" : R$" + formatarNumero(totalSalario));
                }

                System.out.println("Total dos salários: R$" + formatarNumero(totalSalario));
                System.out.println("--------------------");
                // 3.12 - Imprimir quantos salários mínimos ganha cada funcionário
                System.out.println("Salários mínimos por funcionário:");
                BigDecimal divisor = new BigDecimal("1212.00");

                for (Funcionario funcionario : funcionarios) {
                    BigDecimal salarioAtual = funcionario.getSalario();
                    BigDecimal salarioDividido = salarioAtual.divide(divisor, 2, RoundingMode.HALF_UP);
                    funcionario.setSalario(salarioDividido);
                        System.out.println(funcionario.getNome() + ": " + String.format("%.2f",
                                                               salarioDividido).replace('.', ',')
                                                              + " salários mínimos");
                }
               //funcionarios.forEach(funcionario -> {
               //        double salariosMinimos = funcionario.getSalario().divide(new BigDecimal("1212.00", 2,                         RoundingMode.HALF_UP));
               //        System.out.println(funcionario.getNome() + ": " + String.format("%.2f",
               //                        salariosMinimos).replace('.', ',')
               //                        + " salários mínimos");
               //});

        }
}
