package com.upwork;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import com.upwork.model.Customer;
import com.upwork.model.Package;
import com.upwork.model.Purchase;
import com.upwork.model.Service;
import com.upwork.model.Usage;
import com.upwork.service.BillingService;
import com.upwork.service.CustomerService;
import com.upwork.util.FileUtil;
import com.upwork.util.Parser;

public class BillingApplication {
    public static void main(String[] args) throws IOException {
        BillingApplication app = new BillingApplication();
        app.run();
    }

    public void run() throws IOException {
        String outputFile = "src/main/resources/output.txt";

        // Load data
        System.out.println("Please Enter Service and their cost in format services=<service_name>:<cost> , e.g. services=S1:1,S2:2,S3:3");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        Map<String, Service> services = loadServices(input);
        System.out.println("Services and associated cost accepted " + services);

        System.out.println(
                "Please Enter Packages and their cost in format packages=<package_plan>:<name>:<quantity>:<cost> , e.g. packages=Basic:100:100.00,Standard:250:225.00,Premium:500:450.00");
        input = sc.nextLine();
        Map<String, Package> packages = loadPackages(input);
        System.out.println("Packages and associated details accepted " + packages);

        CustomerService customerService = new CustomerService();
        BillingService billingService = new BillingService();

        while (true) {
            System.out.println("Enter 1 for purchase and 2 for Usage");
            input = sc.nextLine();

            if (input.equalsIgnoreCase("1")) {
                System.out.println(
                        "Please Enter purchase and customer in format <customer>,<plan> , e.g. C1,Standard");
                input = sc.nextLine();
                processPurchases(loadPurchases(input).get(0), packages, customerService, billingService);
                System.out.println("Purchase input details accepted " + packages);
            } else if (input.equalsIgnoreCase("2")) {
                System.out.println(
                        "Please Enter Usage and service and price in format <customer>,<plan>,<quantity> , e.g. C1,S1,5");
                input = sc.nextLine();
                processUsages(loadUsages(input).get(0), services, customerService, billingService);
            }

            // Generate and write output after each purchase or usage
            generateAndWriteSummary(customerService, billingService, outputFile);
        }
    }

    private Map<String, Service> loadServices(String filePath) {
        return Parser.parseServices(filePath);
    }

    private Map<String, Package> loadPackages(String filePath) {
        return Parser.parsePackages(filePath);
    }

    private List<Purchase> loadPurchases(String filePath) {
        return Parser.parsePurchases(filePath);
    }

    private List<Usage> loadUsages(String filePath) {
        return Parser.parseUsages(filePath);
    }

    private void processPurchases(Purchase purchase, Map<String, Package> packages,
                                  CustomerService customerService, BillingService billingService) {
        Customer customer = customerService.getOrCreateCustomer(purchase.getCustomerId());
        Package pkg = packages.get(purchase.getPackageName());
        if (Objects.nonNull(pkg)) {
            billingService.processPurchase(customer, pkg);
        } else {
            System.err.println("Package not found: " + purchase.getPackageName());
        }
    }

    private void processUsages(Usage usage, Map<String, Service> services,
                               CustomerService customerService, BillingService billingService) {
        Customer customer = customerService.getOrCreateCustomer(usage.getCustomerId());
        Service service = services.get(usage.getServiceName());
        if (Objects.nonNull(service)) {
            billingService.processUsage(customer, service, usage.getQuantity());
        } else {
            System.err.println("Service not found: " + usage.getServiceName());
        }
    }

    private void generateAndWriteSummary(CustomerService customerService, BillingService billingService,
                                         String outputFile) throws IOException {
        Map<String, Customer> customerMap = customerService.getAllCustomers();
        String output = billingService.generateTransactionSummary(customerMap);
        System.out.println("Output " + output);
        FileUtil.writeToFile(outputFile, output);
        System.out.println("Transaction summary written to " + outputFile);
    }
}
