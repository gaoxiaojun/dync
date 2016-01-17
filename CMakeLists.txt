cmake_minimum_required(VERSION 2.8)
project(hotspot)

# global var
set(TARGET_ARCH amd64)


# set command include paths
include_directories(src/share/vm)

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
cmake_minimum_required(VERSION 2.8)
project(adlc)
include_directories(.. ../precompiled)
add_definitions(-DCOMPILER2 -DTARGET_COMPILER_gcc -DTARGET_ARCH_MODEL_x86_64 -DTARGET_ARCH_x86 -DTARGET_OS_FAMILY_bsd)

set(OPTO_SRC
addnode.cpp
arraycopynode.cpp
block.cpp
buildOopMap.cpp
bytecodeInfo.cpp
c2_globals.cpp
c2compiler.cpp
callGenerator.cpp
callnode.cpp
castnode.cpp
cfgnode.cpp
chaitin.cpp
classes.cpp
coalesce.cpp
compile.cpp
connode.cpp
convertnode.cpp
countbitsnode.cpp
divnode.cpp
doCall.cpp
domgraph.cpp
escape.cpp
gcm.cpp
generateOptoStub.cpp
graphKit.cpp
idealGraphPrinter.cpp
idealKit.cpp
ifg.cpp
ifnode.cpp
indexSet.cpp
intrinsicnode.cpp
lcm.cpp
library_call.cpp
live.cpp
locknode.cpp
loopPredicate.cpp
loopTransform.cpp
loopUnswitch.cpp
loopnode.cpp
loopopts.cpp
machnode.cpp
macro.cpp
macroArrayCopy.cpp
matcher.cpp
mathexactnode.cpp
memnode.cpp
movenode.cpp
mulnode.cpp
multnode.cpp
narrowptrnode.cpp
node.cpp
opaquenode.cpp
opcodes.cpp
output.cpp
parse1.cpp
parse2.cpp
parse3.cpp
parseHelper.cpp
phase.cpp
phaseX.cpp
postaloc.cpp
reg_split.cpp
regalloc.cpp
regmask.cpp
replacednodes.cpp
rootnode.cpp
runtime.cpp
split_if.cpp
stringopts.cpp
subnode.cpp
superword.cpp
type.cpp
vectornode.cpp
)

add_library(opto ${OPTO_SRC})
