/*
 * Copyright (c) 2012, 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package jdk.vm.ci.hotspot.amd64;

import static jdk.vm.ci.inittimer.InitTimer.timer;

import java.util.EnumSet;

import jdk.vm.ci.amd64.AMD64;
import jdk.vm.ci.code.Architecture;
import jdk.vm.ci.code.RegisterConfig;
import jdk.vm.ci.code.TargetDescription;
import jdk.vm.ci.code.stack.StackIntrospection;
import jdk.vm.ci.hotspot.HotSpotCodeCacheProvider;
import jdk.vm.ci.hotspot.HotSpotConstantReflectionProvider;
import jdk.vm.ci.hotspot.HotSpotJVMCIBackendFactory;
import jdk.vm.ci.hotspot.HotSpotJVMCIRuntimeProvider;
import jdk.vm.ci.hotspot.HotSpotMetaAccessProvider;
import jdk.vm.ci.hotspot.HotSpotStackIntrospection;
import jdk.vm.ci.hotspot.HotSpotVMConfig;
import jdk.vm.ci.inittimer.InitTimer;
import jdk.vm.ci.meta.ConstantReflectionProvider;
import jdk.vm.ci.runtime.JVMCIBackend;
import jdk.vm.ci.service.ServiceProvider;

@ServiceProvider(HotSpotJVMCIBackendFactory.class)
public class AMD64HotSpotJVMCIBackendFactory implements HotSpotJVMCIBackendFactory {

    protected EnumSet<AMD64.CPUFeature> computeFeatures(HotSpotVMConfig config) {
        // Configure the feature set using the HotSpot flag settings.
        EnumSet<AMD64.CPUFeature> features = EnumSet.noneOf(AMD64.CPUFeature.class);
        if ((config.x86CPUFeatures & config.cpu3DNOWPREFETCH) != 0) {
            features.add(AMD64.CPUFeature.AMD_3DNOW_PREFETCH);
        }
        assert config.useSSE >= 2 : "minimum config for x64";
        features.add(AMD64.CPUFeature.SSE);
        features.add(AMD64.CPUFeature.SSE2);
        if ((config.x86CPUFeatures & config.cpuSSE3) != 0) {
            features.add(AMD64.CPUFeature.SSE3);
        }
        if ((config.x86CPUFeatures & config.cpuSSSE3) != 0) {
            features.add(AMD64.CPUFeature.SSSE3);
        }
        if ((config.x86CPUFeatures & config.cpuSSE4A) != 0) {
            features.add(AMD64.CPUFeature.SSE4A);
        }
        if ((config.x86CPUFeatures & config.cpuSSE41) != 0) {
            features.add(AMD64.CPUFeature.SSE4_1);
        }
        if ((config.x86CPUFeatures & config.cpuSSE42) != 0) {
            features.add(AMD64.CPUFeature.SSE4_2);
        }
        if ((config.x86CPUFeatures & config.cpuPOPCNT) != 0) {
            features.add(AMD64.CPUFeature.POPCNT);
        }
        if ((config.x86CPUFeatures & config.cpuLZCNT) != 0) {
            features.add(AMD64.CPUFeature.LZCNT);
        }
        if ((config.x86CPUFeatures & config.cpuERMS) != 0) {
            features.add(AMD64.CPUFeature.ERMS);
        }
        if ((config.x86CPUFeatures & config.cpuAVX) != 0) {
            features.add(AMD64.CPUFeature.AVX);
        }
        if ((config.x86CPUFeatures & config.cpuAVX2) != 0) {
            features.add(AMD64.CPUFeature.AVX2);
        }
        if ((config.x86CPUFeatures & config.cpuAES) != 0) {
            features.add(AMD64.CPUFeature.AES);
        }
        if ((config.x86CPUFeatures & config.cpu3DNOWPREFETCH) != 0) {
            features.add(AMD64.CPUFeature.AMD_3DNOW_PREFETCH);
        }
        if ((config.x86CPUFeatures & config.cpuBMI1) != 0) {
            features.add(AMD64.CPUFeature.BMI1);
        }
        if ((config.x86CPUFeatures & config.cpuBMI2) != 0) {
            features.add(AMD64.CPUFeature.BMI2);
        }
        if ((config.x86CPUFeatures & config.cpuRTM) != 0) {
            features.add(AMD64.CPUFeature.RTM);
        }
        if ((config.x86CPUFeatures & config.cpuADX) != 0) {
            features.add(AMD64.CPUFeature.ADX);
        }
        if ((config.x86CPUFeatures & config.cpuAVX512F) != 0) {
            features.add(AMD64.CPUFeature.AVX512F);
        }
        if ((config.x86CPUFeatures & config.cpuAVX512DQ) != 0) {
            features.add(AMD64.CPUFeature.AVX512DQ);
        }
        if ((config.x86CPUFeatures & config.cpuAVX512PF) != 0) {
            features.add(AMD64.CPUFeature.AVX512PF);
        }
        if ((config.x86CPUFeatures & config.cpuAVX512ER) != 0) {
            features.add(AMD64.CPUFeature.AVX512ER);
        }
        if ((config.x86CPUFeatures & config.cpuAVX512CD) != 0) {
            features.add(AMD64.CPUFeature.AVX512CD);
        }
        if ((config.x86CPUFeatures & config.cpuAVX512BW) != 0) {
            features.add(AMD64.CPUFeature.AVX512BW);
        }
        if ((config.x86CPUFeatures & config.cpuAVX512VL) != 0) {
            features.add(AMD64.CPUFeature.AVX512VL);
        }
        return features;
    }

    protected EnumSet<AMD64.Flag> computeFlags(HotSpotVMConfig config) {
        EnumSet<AMD64.Flag> flags = EnumSet.noneOf(AMD64.Flag.class);
        if (config.useCountLeadingZerosInstruction) {
            flags.add(AMD64.Flag.UseCountLeadingZerosInstruction);
        }
        if (config.useCountTrailingZerosInstruction) {
            flags.add(AMD64.Flag.UseCountTrailingZerosInstruction);
        }
        return flags;
    }

    protected TargetDescription createTarget(HotSpotVMConfig config) {
        final int stackFrameAlignment = 16;
        final int implicitNullCheckLimit = 4096;
        final boolean inlineObjects = true;
        Architecture arch = new AMD64(computeFeatures(config), computeFlags(config));
        return new TargetDescription(arch, true, stackFrameAlignment, implicitNullCheckLimit, inlineObjects);
    }

    protected HotSpotConstantReflectionProvider createConstantReflection(HotSpotJVMCIRuntimeProvider runtime) {
        return new HotSpotConstantReflectionProvider(runtime);
    }

    protected RegisterConfig createRegisterConfig(HotSpotJVMCIRuntimeProvider runtime, TargetDescription target) {
        return new AMD64HotSpotRegisterConfig(target.arch, runtime.getConfig());
    }

    protected HotSpotCodeCacheProvider createCodeCache(HotSpotJVMCIRuntimeProvider runtime, TargetDescription target, RegisterConfig regConfig) {
        return new HotSpotCodeCacheProvider(runtime, runtime.getConfig(), target, regConfig);
    }

    protected HotSpotMetaAccessProvider createMetaAccess(HotSpotJVMCIRuntimeProvider runtime) {
        return new HotSpotMetaAccessProvider(runtime);
    }

    @Override
    public String getArchitecture() {
        return "AMD64";
    }

    @Override
    public String toString() {
        return "JVMCIBackend:" + getArchitecture();
    }

    @SuppressWarnings("try")
    public JVMCIBackend createJVMCIBackend(HotSpotJVMCIRuntimeProvider runtime, JVMCIBackend host) {

        assert host == null;
        TargetDescription target = createTarget(runtime.getConfig());

        RegisterConfig regConfig;
        HotSpotCodeCacheProvider codeCache;
        ConstantReflectionProvider constantReflection;
        HotSpotMetaAccessProvider metaAccess;
        StackIntrospection stackIntrospection;
        try (InitTimer t = timer("create providers")) {
            try (InitTimer rt = timer("create MetaAccess provider")) {
                metaAccess = createMetaAccess(runtime);
            }
            try (InitTimer rt = timer("create RegisterConfig")) {
                regConfig = createRegisterConfig(runtime, target);
            }
            try (InitTimer rt = timer("create CodeCache provider")) {
                codeCache = createCodeCache(runtime, target, regConfig);
            }
            try (InitTimer rt = timer("create ConstantReflection provider")) {
                constantReflection = createConstantReflection(runtime);
            }
            try (InitTimer rt = timer("create StackIntrospection provider")) {
                stackIntrospection = new HotSpotStackIntrospection(runtime);
            }
        }
        try (InitTimer rt = timer("instantiate backend")) {
            return createBackend(metaAccess, codeCache, constantReflection, stackIntrospection);
        }
    }

    protected JVMCIBackend createBackend(HotSpotMetaAccessProvider metaAccess, HotSpotCodeCacheProvider codeCache, ConstantReflectionProvider constantReflection, StackIntrospection stackIntrospection) {
        return new JVMCIBackend(metaAccess, codeCache, constantReflection, stackIntrospection);
    }
}
