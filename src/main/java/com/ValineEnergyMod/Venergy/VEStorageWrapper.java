package com.ValineEnergyMod.Venergy;

import net.minecraftforge.energy.IEnergyStorage;
import java.math.BigInteger;

public class VEStorageWrapper implements IEnergyStorage {
    private final VEStorage veStorage;

    public VEStorageWrapper(VEStorage veStorage) {
        this.veStorage = veStorage;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        VEStorage.Result result = veStorage.receiveEnergy(VEStorage.feTove(BigInteger.valueOf(maxReceive)), simulate);
        return VEStorage.veToFe(result.received).min(BigInteger.valueOf(Integer.MAX_VALUE)).intValue();
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        BigInteger extracted = veStorage.extractEnergy(VEStorage.feTove(BigInteger.valueOf(maxExtract)), simulate);
        return VEStorage.veToFe(extracted).min(BigInteger.valueOf(Integer.MAX_VALUE)).intValue();
    }

    @Override
    public int getEnergyStored() {
        return VEStorage.veToFe(veStorage.getEnergyStored()).min(BigInteger.valueOf(Integer.MAX_VALUE)).intValue();
    }

    @Override
    public int getMaxEnergyStored() {
        return Integer.MAX_VALUE; // VEの最大値はintで表現できないため、Integer.MAX_VALUEを返す
    }

    @Override
    public boolean canExtract() {
        return veStorage.canExtract();
    }

    @Override
    public boolean canReceive() {
        return veStorage.canReceive();
    }

    // VE versions of the methods
    public BigInteger receiveEnergyVE(BigInteger maxReceive, boolean simulate) {
        // veStorage が null でないことを確認
        if (veStorage == null) {
            // エラー処理
            return BigInteger.ZERO; // 例: 0 を返す
        }

        // receiveEnergy メソッドの引数を確認
        VEStorage.Result result = veStorage.receiveEnergy(maxReceive, simulate);
        return result.received; // 実際に受け取られたエネルギーを返す
    }

    public BigInteger extractEnergyVE(BigInteger maxExtract, boolean simulate) {
        return veStorage.extractEnergy(maxExtract, simulate);
    }

    public BigInteger getEnergyStoredVE() {
        return veStorage.getEnergyStored();
    }

    public BigInteger getMaxEnergyStoredVE() {
        return veStorage.getMaxEnergyStored();
    }

}



