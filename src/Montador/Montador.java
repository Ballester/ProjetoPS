/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Montador;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ballester
 */
public class Montador {
    
    Scanner scanner;
    HashMap<String, String> instructions;
    HashMap<String, Integer> labels;
    int pc;
    
    

    public static void main(String[] args) {
        Montador montador = new Montador();
        String inst;
        while(montador.scanner.hasNextLine()){
            inst = montador.scanner.nextLine();
            montador.convertToCode(inst.toString());
        }

    }
    
    public Montador() {
        this.instructions = new HashMap<String, String>();
        this.labels = new HashMap<String, Integer>();

        try { 
            scanner = new Scanner(new File("/home/ballester/Documents/trabalhos/ProjetoPS/src/input_montador"));
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Montador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        fillInstructions();
        
    }
    
    //integer is how many jumps a pc do
    public void fillInstructions() {
        this.instructions.put("NOP", "00000000");
        this.instructions.put("HLT", "11110000");
        
        //CONDICIONAL
        this.instructions.put("BR", "00110000DDDDDDDD");
        this.instructions.put("BNE", "00110001DDDDDDDD");
        this.instructions.put("BEQ", "00110010DDDDDDDD");
        this.instructions.put("BPL", "00110011DDDDDDDD");
        this.instructions.put("BMI", "00110100DDDDDDDD");
        this.instructions.put("BVC", "00110101DDDDDDDD");
        this.instructions.put("BVS", "00110110DDDDDDDD");
        this.instructions.put("BCC", "00110111DDDDDDDD");
        
        //1 operando
        this.instructions.put("CLR", "10000000XXMMMRRR");
        this.instructions.put("NOT", "10000001XXMMMRRR");
        this.instructions.put("INC", "10000010XXMMMRRR");
        this.instructions.put("DEC", "10000011XXMMMRRR");
        this.instructions.put("NEG", "10000100XXMMMRRR");
        this.instructions.put("TST", "10000101XXMMMRRR");

        //2 operandos
        this.instructions.put("MOV", "1001MMMRRRMMMRRR");
        this.instructions.put("ADD", "1010MMMRRRMMMRRR");
        this.instructions.put("SUB", "1011MMMRRRMMMRRR");
        this.instructions.put("CMP", "1100MMMRRRMMMRRR");
        this.instructions.put("AND", "1101MMMRRRMMMRRR");
        this.instructions.put("OR", "1110MMMRRRMMMRRR");

        

    }
    
    public void convertToCode(String inst) {
        String[] parts = inst.split(" ");
        int valid = 0;
        int i=0;
//        System.out.println(inst.replaceFirst(":", ""));
        if (parts[0].contains(":")) {
            String replaced = parts[0].replaceFirst(":", "");
            this.labels.put(replaced, this.pc);
            i++;
        }
        String trans =  this.instructions.get(parts[i]);
        String trans_copy = trans.toString();
        i++;
        try {
            String[] ops = parts[i].split(",");
            System.out.println(trans_copy + "aqui");
            if (trans_copy.indexOf("MMM") != -1) {
                trans_copy = trans_copy.replaceFirst("MMM", opToBinary(ops[0], 3));
                trans_copy = trans_copy.replaceFirst("RRR", opToBinary(ops[1], 3));
            }
            if (trans_copy.indexOf("MMM") != -1) {
                trans_copy = trans_copy.replaceFirst("MMM", opToBinary(ops[2], 3));
                trans_copy = trans_copy.replaceFirst("RRR", opToBinary(ops[3], 3));
            }
            if (trans_copy.indexOf("DDDDDDDD") != -1) {
                trans_copy = trans_copy.replace("DDDDDDDD", opToBinary(ops[2], 8));
            }

//            System.out.println(trans_copy);
           
        }
        catch(Exception e) {
            System.out.println(e);
        }
        
        System.out.println(trans_copy);
        this.pc += inst.length()/4;
                
    }
    
    public String opToBinary(String op, int n) {
        System.out.println("OP: " + op);
        try {
            return String.format("%3s", Integer.toBinaryString(Integer.parseInt(op))).replace(' ', '0');
//        return str.substring(str.length()-4, str.length()-1);
        }
        catch(Exception e) {
            System.out.println(labels);
            System.out.println("OP2: " + this.labels.get(op));
            //aqui implementa quando é uma label ao invés de um número
            return String.format("%3s", Integer.toBinaryString(this.labels.get(op))).replace(' ', '0');
        }
    }
    
    

}
