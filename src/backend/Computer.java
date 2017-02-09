/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Computer {
    
    public Stack sp;
    public short pc, acc;
    public short ri, re;
    byte modOp;
    FileInputStream fin;
    DataInputStream din;
    Instructions inst;
    public Memory memoria;
            
    public Computer(Memory memoria) {
        this.sp = new Stack();
        this.pc = 0;
        this.acc = this.ri = this.re = 0;
        //this.modOp = modoDeOperacao;
        this.inst = new Instructions();
        this.memoria = memoria;
        
        

    }
    
    public void reset(){
        this.sp = new Stack();
        this.pc = 0;
        this.acc = this.ri = this.re = 0;
    }
    
    public void run(){
        short opcode = 0;
        int mod1=0, mod2=0;
        short op1, op2;
        
        if (this.modOp == 0) {
            opcode = memoria.load(pc++);
            
            //System.out.println("opcode: " + opcode);
            
            if (opcode > 31 && opcode < 128) {
				if (opcode > 97) {
					opcode -= 96;
					mod1 = 1;
					mod2 = 1;
				}
				else if (opcode > 63) {
					opcode -= 64;
                    mod1 = 1;
                    mod2 = 1;
                }
                else {
                    opcode -= 32;
                    mod1 = 1;
                }
            }
            else if (opcode >= 128) {
                opcode -= 128;
                if (opcode == 13 || opcode == 45) {//copy
                    mod2 = 2;
                    if (opcode == 45) {
                        opcode -= 32;
                        mod1 = 1;
                    }
                }
                else {
                    mod1 = 2;
                }
            }
            
            switch(opcode) {
                case 0:
                    op1 = memoria.load(pc++);
                    re = op1;
                    if (mod1 == 0) {
                        pc = inst.br(op1);
                    }
                    else {
                        pc = inst.br(inst.indToDir(memoria, op1));
                    }
                    break;
                    
                case 1:
                    op1 = memoria.load(pc++);
                    re = op1;
                    if (mod1 == 0) {
                        pc = inst.brPos(pc, op1, this.acc);
                    }
                    else {
                        pc = inst.brPos(pc, inst.indToDir(memoria, op1), this.acc);
                    }
                    break;
                    
                case 2:
                    op1 = memoria.load(pc++);
                    re = op1;
                    if (mod1 == 0) {
                        acc = inst.add(acc, inst.dirToIm(memoria, op1));
                    }
                    else if (mod1 == 1) {
                        acc = inst.add(acc, inst.indToIm(memoria, op1));
                    }
                    else {
                        acc = inst.add(acc, op1);
                    }
                    break;
                    
                case 3:
                    op1 = memoria.load(pc++);
                    re = op1;
                    if (mod1 == 0) {
                        acc = inst.load( inst.dirToIm(memoria, op1));
                        //System.out.println("acumulador: " + acc);
                    }
                    else if (mod1 == 1) {
                        acc = inst.load(inst.indToIm(memoria, op1));
                    }
                    else {
                        acc = inst.load(op1);
                    }
                    break;
                    
                case 4:
                    op1 = memoria.load(pc++);
                    re = op1;
                    if (mod1 == 0) {
                        pc = inst.brZero(pc, op1, acc);
                    }
                    else if (mod1 == 1) {
                        pc = inst.brZero(pc, inst.indToDir(memoria, op1), acc);
                    }
                    break;
                    
                case 5:
                    op1 = memoria.load(pc++);
                    re = op1;
                    if (mod1 == 0) {
                        pc = inst.brNeg(pc, op1, acc);
                    }
                    else if (mod1 == 1) {
                        pc = inst.brNeg(pc, inst.indToDir(memoria, op1), acc);
                    }
                    break;
                    
                case 6:
                    op1 = memoria.load(pc++);
                    re = op1;
                    if (mod1 == 0) {
                        acc = inst.sub(acc, inst.dirToIm(memoria, op1));
                    }
                    else if (mod1 == 1) {
                        acc = inst.sub(acc, inst.indToIm(memoria, op1));
                    }
                    else {
                        acc = inst.sub(acc, op1);
                    }
                    break;
				
		case 7:
                    op1 = memoria.load(pc++);
                    re = op1;
                    if (mod1 == 0) {
                        //inst.store(memoria, inst.dirToIm(memoria, op1), acc);
                        //memoria.store(acc, inst.dirToIm(memoria, op1));
                        memoria.store(acc, op1);
                    }
                    else{
                        //inst.store(memoria, inst.indToIm(memoria, op1), acc);
                        memoria.store(acc, inst.indToIm(memoria, op1));
                    }
                    break;
				
		case 8:
                    op1 = memoria.load(pc++);
                    re = op1;
                    if (mod1 == 0) {
                        inst.write(inst.dirToIm(memoria, op1));
                    }
                    else if (mod1 == 1) {
                        inst.write(inst.indToIm(memoria, op1));
                    }
                    else {
                        inst.write(op1);
                    }
                    break;
					
		case 10:
                    op1 = memoria.load(pc++);
                    re = op1;
                    if (mod1 == 0) {
                        acc = inst.divide(acc, inst.dirToIm(memoria, op1));
                    }
                    else if (mod1 == 1) {
                        acc = inst.divide(acc, inst.indToIm(memoria, op1));
                    }
                    else {
                        acc = inst.divide(acc, op1);
                    }
                    break;
					
		case 11:
                    inst.stop();
				
		case 12:
                    op1 = memoria.load(pc++);
                    re = op1;
                    if (mod1 == 0) {
                        inst.read();
                    }
                    else{
                        inst.read();
                    }
                    break;
				
		case 13:
                    op1 = memoria.load(pc++);
                    re = op1;
                    op2 = memoria.load(pc++);
                    re = op2;  
                    if (mod1 == 0 && mod2 == 0) {
                        inst.copy(memoria, inst.dirToIm(memoria, op1), inst.dirToIm(memoria, op2));
                    }
                    else if (mod1 == 0 && mod2 == 1) {
                        inst.copy(memoria, inst.dirToIm(memoria, op1), inst.indToIm(memoria, op1));
                    }
					else if (mod1 == 0 && mod2 == 2) {
                        inst.copy(memoria, inst.dirToIm(memoria, op1), op2);
                    }
                    if (mod1 == 1 && mod2 == 0) {
                        inst.copy(memoria, inst.indToIm(memoria, op1), inst.dirToIm(memoria, op2));
                    }
                    else if (mod1 == 1 && mod2 == 1) {
                        inst.copy(memoria, inst.indToIm(memoria, op1), inst.indToIm(memoria, op1));
                    }
					else if (mod1 == 1 && mod2 == 2) {
                        inst.copy(memoria, inst.indToIm(memoria, op1), op2);
                    }
                    break;
				
				case 14:
                    op1 = memoria.load(pc++);
                    re = op1;
                    if (mod1 == 0) {
                        acc = inst.mult(acc, inst.dirToIm(memoria, op1));
                    }
                    else if (mod1 == 1) {
                        acc = inst.mult(acc, inst.indToIm(memoria, op1));
                    }
                    else {
                        acc = inst.mult(acc, op1);
                    }
                    break;
				
		case 15:
                    op1 = memoria.load(pc++);
                    re = op1;
                    if (mod1 == 0) {
                        sp = inst.call(sp, acc);
                        pc = inst.dirToIm(memoria, op1);
                    }
                    else{
                        sp = inst.call(sp, acc);
                        pc = inst.indToIm(memoria, op1);
                        
                    }
                    break;
					
                case 16:
                    pc = inst.ret(sp, pc);
                    sp.pop();
                
            }
            
        }
        
        ri = memoria.load(pc); //atualiza Registrador de Instrucao
    }
    
}