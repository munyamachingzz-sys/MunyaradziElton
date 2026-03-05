/**
 * PAYE Tax Calculation System
 * BIT 213: Introduction to Programming - Java
 * Tutorial: Unit 3 - Functions and Parameter Passing
 * Developer: Longwani C Perry (Mr.)
 */

import java.util.Scanner;

public class PAYETaxSystem {
    
    // Tax brackets and rates based on Zambian tax system (example rates)
    private static final double TAX_FREE_THRESHOLD = 4000.00; // First K4,000 tax-free
    private static final double BRACKET1_MAX = 4800.00;      // K4,001 - K4,800
    private static final double BRACKET2_MAX = 6900.00;      // K4,801 - K6,900
    private static final double BRACKET3_MAX = 20000.00;     // K6,901 - K20,000
    private static final double BRACKET4_MAX = 50000.00;     // K20,001 - K50,000
    
    private static final double RATE1 = 0.20;  // 20% for first bracket
    private static final double RATE2 = 0.25;  // 25% for second bracket
    private static final double RATE3 = 0.30;  // 30% for third bracket
    private static final double RATE4 = 0.35;  // 35% for fourth bracket
    private static final double RATE5 = 0.37;  // 37% for above K50,000
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("========================================");
        System.out.println("   PERLONGS PAYE TAX CALCULATION SYSTEM");
        System.out.println("========================================\n");
        
        boolean continueCalculation = true;
        
        while (continueCalculation) {
            // Get employee details
            System.out.print("Enter employee name: ");
            String employeeName = scanner.nextLine();
            
            System.out.print("Enter employee ID: ");
            String employeeId = scanner.nextLine();
            
            System.out.print("Enter gross monthly salary (K): ");
            double grossSalary = scanner.nextDouble();
            
            // Calculate tax components using functions
            double taxableIncome = calculateTaxableIncome(grossSalary);
            double payeTax = calculatePAYE(taxableIncome);
            double napsaContribution = calculateNAPSA(grossSalary);
            double netSalary = calculateNetSalary(grossSalary, payeTax, napsaContribution);
            
            // Display results
            displayPaySlip(employeeName, employeeId, grossSalary, taxableIncome, 
                          payeTax, napsaContribution, netSalary);
            
            // Ask if user wants to calculate another employee's tax
            System.out.print("\nCalculate tax for another employee? (yes/no): ");
            scanner.nextLine(); // Consume newline
            String response = scanner.nextLine().toLowerCase();
            continueCalculation = response.equals("yes") || response.equals("y");
            
            System.out.println();
        }
        
        System.out.println("Thank you for using PERLONGS PAYE System!");
        scanner.close();
    }
    
    /**
     * Function to calculate taxable income after deducting tax-free threshold
     * @param grossSalary The gross monthly salary
     * @return The taxable income
     */
    public static double calculateTaxableIncome(double grossSalary) {
        if (grossSalary <= TAX_FREE_THRESHOLD) {
            return 0;
        } else {
            return grossSalary - TAX_FREE_THRESHOLD;
        }
    }
    
    /**
     * Function to calculate PAYE tax based on income brackets
     * @param taxableIncome The income after tax-free threshold
     * @return The calculated PAYE tax amount
     */
    public static double calculatePAYE(double taxableIncome) {
        double tax = 0;
        
        if (taxableIncome <= 0) {
            return 0;
        }
        
        // Bracket 1: First K800 above tax-free (K4,001 - K4,800)
        if (taxableIncome <= (BRACKET1_MAX - TAX_FREE_THRESHOLD)) {
            tax = taxableIncome * RATE1;
        } 
        // Bracket 2: Next K2,100 (K4,801 - K6,900)
        else if (taxableIncome <= (BRACKET2_MAX - TAX_FREE_THRESHOLD)) {
            tax = (800 * RATE1) + 
                  ((taxableIncome - 800) * RATE2);
        }
        // Bracket 3: Next K13,100 (K6,901 - K20,000)
        else if (taxableIncome <= (BRACKET3_MAX - TAX_FREE_THRESHOLD)) {
            tax = (800 * RATE1) + 
                  (2100 * RATE2) + 
                  ((taxableIncome - 2900) * RATE3);
        }
        // Bracket 4: Next K30,000 (K20,001 - K50,000)
        else if (taxableIncome <= (BRACKET4_MAX - TAX_FREE_THRESHOLD)) {
            tax = (800 * RATE1) + 
                  (2100 * RATE2) + 
                  (13100 * RATE3) + 
                  ((taxableIncome - 16000) * RATE4);
        }
        // Bracket 5: Above K50,000
        else {
            tax = (800 * RATE1) + 
                  (2100 * RATE2) + 
                  (13100 * RATE3) + 
                  (30000 * RATE4) + 
                  ((taxableIncome - 46000) * RATE5);
        }
        
        return tax;
    }
    
    /**
     * Function to calculate NAPSA contribution (5% of gross salary)
     * @param grossSalary The gross monthly salary
     * @return The NAPSA contribution amount
     */
    public static double calculateNAPSA(double grossSalary) {
        final double NAPSA_RATE = 0.05; // 5% contribution
        final double NAPSA_MAX = 600.00; // Maximum monthly contribution
        
        double contribution = grossSalary * NAPSA_RATE;
        
        // Apply maximum cap if applicable
        if (contribution > NAPSA_MAX) {
            return NAPSA_MAX;
        }
        
        return contribution;
    }
    
    /**
     * Function to calculate net salary after deductions
     * @param grossSalary The gross monthly salary
     * @param payeTax The PAYE tax amount
     * @param napsa The NAPSA contribution amount
     * @return The net salary
     */
    public static double calculateNetSalary(double grossSalary, double payeTax, double napsa) {
        return grossSalary - payeTax - napsa;
    }
    
    /**
     * Function to determine tax bracket description
     * @param grossSalary The gross monthly salary
     * @return String describing the tax bracket
     */
    public static String getTaxBracketDescription(double grossSalary) {
        if (grossSalary <= TAX_FREE_THRESHOLD) {
            return "Tax-Free Bracket (Below K4,000)";
        } else if (grossSalary <= BRACKET1_MAX) {
            return "Bracket 1 (K4,001 - K4,800) - 20%";
        } else if (grossSalary <= BRACKET2_MAX) {
            return "Bracket 2 (K4,801 - K6,900) - 25%";
        } else if (grossSalary <= BRACKET3_MAX) {
            return "Bracket 3 (K6,901 - K20,000) - 30%";
        } else if (grossSalary <= BRACKET4_MAX) {
            return "Bracket 4 (K20,001 - K50,000) - 35%";
        } else {
            return "Bracket 5 (Above K50,000) - 37%";
        }
    }
    
    /**
     * Function to display formatted payslip
     * @param name Employee name
     * @param id Employee ID
     * @param gross Gross salary
     * @param taxable Taxable income
     * @param paye PAYE tax amount
     * @param napsa NAPSA contribution
     * @param net Net salary
     */
    public static void displayPaySlip(String name, String id, double gross, 
                                     double taxable, double paye, double napsa, double net) {
        System.out.println("\n========================================");
        System.out.println("           PERLONGS PAYSLIP");
        System.out.println("========================================");
        System.out.printf("Employee: %s (ID: %s)%n", name, id);
        System.out.printf("Tax Bracket: %s%n", getTaxBracketDescription(gross));
        System.out.println("----------------------------------------");
        System.out.printf("%-25s: K%10.2f%n", "Gross Salary", gross);
        System.out.printf("%-25s: K%10.2f%n", "Less: Tax-free threshold", 
                         gross > TAX_FREE_THRESHOLD ? TAX_FREE_THRESHOLD : gross);
        System.out.printf("%-25s: K%10.2f%n", "Taxable Income", taxable);
        System.out.println("----------------------------------------");
        System.out.printf("%-25s: K%10.2f%n", "PAYE Tax", paye);
        System.out.printf("%-25s: K%10.2f%n", "NAPSA Contribution (5%)", napsa);
        System.out.printf("%-25s: K%10.2f%n", "Total Deductions", paye + napsa);
        System.out.println("========================================");
        System.out.printf("%-25s: K%10.2f%n", "NET SALARY", net);
        System.out.println("========================================");
    }
    
    /**
     * Function to calculate annual tax projection
     * @param monthlySalary The monthly salary
     * @return Array containing annual gross, tax, and net
     */
    public static double[] calculateAnnualProjection(double monthlySalary) {
        double annualGross = monthlySalary * 12;
        double monthlyTax = calculatePAYE(calculateTaxableIncome(monthlySalary));
        double annualTax = monthlyTax * 12;
        double annualNapsa = calculateNAPSA(monthlySalary) * 12;
        double annualNet = annualGross - annualTax - annualNapsa;
        
        return new double[]{annualGross, annualTax, annualNapsa, annualNet};
    }
}
