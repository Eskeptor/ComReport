package javabasic.ex14.original;

class PNGRowInfo {
    public  PNGRowInfo () {}

    public int      width       = 0;    /* width of row */
    public int      rowbytes    = 0;    /* number of bytes in row */
    public byte     color_type  = 0;    /* color type of row */
    public byte     bit_depth   = 0;    /* bit depth of row */
    public byte     channels    = 0;    /* number of channels (1, 2, 3, or 4) */
    public byte     pixel_depth = 0;    /* bits per pixel (depth * channels) */
    
}