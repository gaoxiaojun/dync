cmake_minimum_required(VERSION 2.8)
project(hotspot)

# global var
set(TARGET_ARCH x86_64)
set(PLATFORM_ARCH_MODE bsd_x86_64)

# set command include paths
include_directories(src/share/vm src/share/vm/precompiled)
add_definitions(-DCOMPILER2 -DTARGET_COMPILER_gcc -DTARGET_ARCH_MODEL_${TARGET_ARCH} -DTARGET_ARCH_x86 -DTARGET_OS_FAMILY_bsd)

# set adlc 

# generate adlc cpp 

# set c2 compiler include paths
set(C2_INCLUDE
src/share/vm/opto 
src/share/vm/libadt
)


set(C2_SRC
bcEscapeAnalyzer.cpp
)

set(ADLC_SRC
src/share/vm/adlc/adlparse.cpp
src/share/vm/adlc/archDesc.cpp
src/share/vm/adlc/arena.cpp
src/share/vm/adlc/dfa.cpp
src/share/vm/adlc/dict2.cpp
src/share/vm/adlc/filebuff.cpp
src/share/vm/adlc/forms.cpp
src/share/vm/adlc/formsopt.cpp
src/share/vm/adlc/formssel.cpp
src/share/vm/adlc/main.cpp
src/share/vm/adlc/output_c.cpp
src/share/vm/adlc/output_h.cpp
src/share/vm/opto/opcodes.cpp
)

add_executable(adlc ${ADLC_SRC})

set(OPTO_SRC
src/share/vm/opto/addnode.cpp
src/share/vm/opto/arraycopynode.cpp
src/share/vm/opto/block.cpp
src/share/vm/opto/buildOopMap.cpp
src/share/vm/opto/bytecodeInfo.cpp
src/share/vm/opto/c2_globals.cpp
src/share/vm/opto/c2compiler.cpp
src/share/vm/opto/callGenerator.cpp
src/share/vm/opto/callnode.cpp
src/share/vm/opto/castnode.cpp
src/share/vm/opto/cfgnode.cpp
src/share/vm/opto/chaitin.cpp
src/share/vm/opto/classes.cpp
src/share/vm/opto/coalesce.cpp
src/share/vm/opto/compile.cpp
src/share/vm/opto/connode.cpp
src/share/vm/opto/convertnode.cpp
src/share/vm/opto/countbitsnode.cpp
src/share/vm/opto/divnode.cpp
src/share/vm/opto/doCall.cpp
src/share/vm/opto/domgraph.cpp
src/share/vm/opto/escape.cpp
src/share/vm/opto/gcm.cpp
src/share/vm/opto/generateOptoStub.cpp
src/share/vm/opto/graphKit.cpp
src/share/vm/opto/idealGraphPrinter.cpp
src/share/vm/opto/idealKit.cpp
src/share/vm/opto/ifg.cpp
src/share/vm/opto/ifnode.cpp
src/share/vm/opto/indexSet.cpp
src/share/vm/opto/intrinsicnode.cpp
src/share/vm/opto/lcm.cpp
src/share/vm/opto/library_call.cpp
src/share/vm/opto/live.cpp
src/share/vm/opto/locknode.cpp
src/share/vm/opto/loopPredicate.cpp
src/share/vm/opto/loopTransform.cpp
src/share/vm/opto/loopUnswitch.cpp
src/share/vm/opto/loopnode.cpp
src/share/vm/opto/loopopts.cpp
src/share/vm/opto/machnode.cpp
src/share/vm/opto/macro.cpp
src/share/vm/opto/macroArrayCopy.cpp
src/share/vm/opto/matcher.cpp
src/share/vm/opto/mathexactnode.cpp
src/share/vm/opto/memnode.cpp
src/share/vm/opto/movenode.cpp
src/share/vm/opto/mulnode.cpp
src/share/vm/opto/multnode.cpp
src/share/vm/opto/narrowptrnode.cpp
src/share/vm/opto/node.cpp
src/share/vm/opto/opaquenode.cpp
src/share/vm/opto/opcodes.cpp
src/share/vm/opto/output.cpp
src/share/vm/opto/parse1.cpp
src/share/vm/opto/parse2.cpp
src/share/vm/opto/parse3.cpp
src/share/vm/opto/parseHelper.cpp
src/share/vm/opto/phase.cpp
src/share/vm/opto/phaseX.cpp
src/share/vm/opto/postaloc.cpp
src/share/vm/opto/reg_split.cpp
src/share/vm/opto/regalloc.cpp
src/share/vm/opto/regmask.cpp
src/share/vm/opto/replacednodes.cpp
src/share/vm/opto/rootnode.cpp
src/share/vm/opto/runtime.cpp
src/share/vm/opto/split_if.cpp
src/share/vm/opto/stringopts.cpp
src/share/vm/opto/subnode.cpp
src/share/vm/opto/superword.cpp
src/share/vm/opto/type.cpp
src/share/vm/opto/vectornode.cpp
)

#add_library(opto ${OPTO_SRC})
