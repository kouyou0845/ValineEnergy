package com.ValineEnergyMod.Venergy;

import java.math.BigInteger;

public class VEStorage {
    private static final BigInteger MAX_VE = BigInteger.valueOf(2).pow(2147483647).subtract(BigInteger.ONE);
    private static final BigInteger VE_TO_FE_RATIO = new BigInteger("2147483647"); // 21億

    private BigInteger energy;

    public VEStorage() {
        this.energy = BigInteger.ZERO;
    }

    public Result receiveEnergy(BigInteger maxReceive, boolean simulate) {
        if (!canReceive()) {
            return new Result(BigInteger.ZERO, maxReceive);
        }

        BigInteger energyReceived = MAX_VE.subtract(energy).min(maxReceive);
        if (!simulate) {
            energy = energy.add(energyReceived);
        }
        return new Result(energyReceived, maxReceive.subtract(energyReceived));
    }

    public BigInteger extractEnergy(BigInteger maxExtract, boolean simulate) {
        if (!canExtract()) {
            return BigInteger.ZERO;
        }

        BigInteger energyExtracted = energy.min(maxExtract);
        if (!simulate) {
            energy = energy.subtract(energyExtracted);
        }
        return energyExtracted;
    }

    public BigInteger getEnergyStored() {
        return energy;
    }

    public BigInteger getMaxEnergyStored() {
        return MAX_VE;
    }

    public boolean canExtract() {
        return energy.compareTo(BigInteger.ZERO) > 0;
    }

    public boolean canReceive() {
        return energy.compareTo(MAX_VE) < 0;
    }

    // VE to FE conversion
    public static BigInteger veToFe(BigInteger ve) {
        return ve.multiply(VE_TO_FE_RATIO);
    }

    // FE to GVE conversion
    public static BigInteger feTove(BigInteger fe) {
        return fe.divide(VE_TO_FE_RATIO);
    }

    public static class Result {
        public final BigInteger received;
        public final BigInteger rejected;

        public Result(BigInteger received, BigInteger rejected) {
            this.received = received;
            this.rejected = rejected;
        }
    }
}
