package com.vawn.restaurant_management.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {

  public static byte[] compressImage(byte[] image) {
    Deflater deflater = new Deflater();
    deflater.setLevel(Deflater.BEST_COMPRESSION);
    deflater.setInput(image);
    deflater.finish();

    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(image.length)) {
      byte[] buffer = new byte[4 * 1024];
      while (!deflater.finished()) {
        int count = deflater.deflate(buffer);
        outputStream.write(buffer, 0, count);
      }
      return outputStream.toByteArray();
    } catch (IOException e) {
      // Log the exception
      e.printStackTrace(); // hoặc sử dụng logger
      return new byte[0]; // Trả về mảng byte rỗng trong trường hợp lỗi
    }
  }

  public static byte[] decompressImage(byte[] image) {
    Inflater inflater = new Inflater();
    inflater.setInput(image);
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(image.length)) {
      byte[] buffer = new byte[4 * 1024];
      while (!inflater.finished()) {
        int count = inflater.inflate(buffer);
        outputStream.write(buffer, 0, count);
      }
      return outputStream.toByteArray();
    } catch (IOException e) {
      // Log the exception
      e.printStackTrace(); // hoặc sử dụng logger
      return new byte[0]; // Trả về mảng byte rỗng trong trường hợp lỗi
    } catch (Exception e) {
      // Đảm bảo xử lý bất kỳ lỗi nào khác
      e.printStackTrace();
      return new byte[0];
    }
  }
}
