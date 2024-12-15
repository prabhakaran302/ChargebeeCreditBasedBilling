package com.upwork.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.upwork.model.Package;
import com.upwork.model.Purchase;
import com.upwork.model.Service;
import com.upwork.model.Usage;

public class Parser {
    public static Map<String, Service> parseServices(String servicesArg) {
        if (!servicesArg.startsWith("services=")) {
            throw new IllegalArgumentException("Invalid services argument. Expected format: services=S1:1,S2:2,S3:3");
        }

        return Arrays.stream(servicesArg.substring("services=".length()).split(",")).map(entry -> entry.split(":"))
                .collect(Collectors.toMap(parts -> parts[0], parts -> new Service(parts[0], Integer.parseInt(parts[1]))));
    }

    public static Map<String, Package> parsePackages(String packagesArg) {
        if (!packagesArg.startsWith("packages=")) {
            throw new IllegalArgumentException("Invalid packages argument. Expected format: packages=Basic:100:100.00,Standard:250:225.00");
        }

        return Arrays.stream(packagesArg.substring("packages=".length()).split(",")).map(entry -> entry.split(":")).collect(
                Collectors.toMap(parts -> parts[0], parts -> new Package(parts[0], Integer.parseInt(parts[1]), Double.parseDouble(parts[2]))));
    }

    public static List<Purchase> parsePurchases(String purchaseArg) {
        return Arrays.stream(purchaseArg.split("\n")).map(line -> {
            String[] parts = line.split(",");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid purchase format: " + line);
            }
            return new Purchase(parts[0], parts[1]);
        }).toList();
    }

    public static List<Usage> parseUsages(String usageArg) {
        return Arrays.stream(usageArg.split("\n")).map(line -> {
            String[] parts = line.split(",");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid usage format: " + line);
            }
            return new Usage(parts[0], parts[1], Integer.parseInt(parts[2]));
        }).toList();
    }
}
