package javabasic.ex14.original;/* Defines some constants used in classes that handle the
 * PBM, PGM and PPM image formats. */

public interface PPMConstants {

  static final String PBM_SIG = "P1";
  static final String PGM_SIG = "P2";
  static final String PPM_SIG = "P3";

  static final int TYPE_UNKNOWN = 0;
  static final int TYPE_PBM = 1;
  static final int TYPE_PGM = 2;
  static final int TYPE_PPM = 3;

}