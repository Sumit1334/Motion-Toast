# Add any ProGuard configurations specific to this
# extension here.

-keep public class com.sumit1334.motiontoast.MotionToast {
    public *;
 }
-keeppackagenames gnu.kawa**, gnu.expr**

-optimizationpasses 4
-allowaccessmodification
-mergeinterfacesaggressively

-repackageclasses 'com/sumit1334/motiontoast/repack'
-flattenpackagehierarchy
-dontpreverify
