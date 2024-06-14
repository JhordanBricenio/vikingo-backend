package com.codej.licoreria.service.impl;

import com.codej.licoreria.service.IUploadService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadServiceImpl implements IUploadService {
    private final static String DIRECTORIO_UPLOAD="C:/vikingo/imagenes";
    private final static String DIRECTORIO_EXTERNO = "/opt/uploads/";

    @Override
    public Resource cargar(String foto) throws MalformedURLException {
        Path rutaArchivo= getPath(foto);
        Resource recurso= new UrlResource(rutaArchivo.toUri());

        if(!recurso.exists() && !recurso.isReadable()){
            rutaArchivo= Paths.get("C:/vikingo/imagenes/").resolve("nofoto.jpg").toAbsolutePath();
            recurso= new UrlResource(rutaArchivo.toUri());
        }
        return  recurso;
    }

    @Override
    public String copiar(MultipartFile archivo) throws IOException {
        String nombreArchivo= UUID.randomUUID().toString()+ "_"+ archivo.getOriginalFilename().
                replace(" ","");
        Path rutaArchiva= getPath(nombreArchivo);
        Files.copy(archivo.getInputStream(), rutaArchiva);
        return nombreArchivo;
    }

    @Override
    public boolean eliminar(String nombreFoto) {
        if(nombreFoto!=null && nombreFoto.length()>0){
            Path rutaFotoAnterior=Paths.get("C:/vikingo/imagenes/").resolve(nombreFoto).toAbsolutePath();
            File archivoFotoAnterior= rutaFotoAnterior.toFile();
            if(archivoFotoAnterior.exists() &&archivoFotoAnterior.canRead()){
                archivoFotoAnterior.delete();
                return true;
            }

        }
        return false;
    }

    @Override
    public Path getPath(String nombreFoto) {
        return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
    }
}