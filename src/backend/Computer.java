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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author pedro
 */
public class Computer {
    
    public Stack sp;
    public short pc, acc;
    public short re;
    public boolean N, Z, V, C;
    public String ri;
    byte modOp;
    FileInputStream fin;
    DataInputStream din;
    Instructions inst;
    public Memory memoria;
    public Memory regs;
            
    public Computer(Memory memoria, Memory registradores) {
        this.sp = new Stack();
        this.pc = 0;
        this.acc = this.re = 0;
        this.ri = "";
        this.N = this.Z = this.V = this.C = false;
        
        //this.modOp = modoDeOperacao;
        this.inst = new Instructions();
        this.memoria = memoria;
        this.regs = registradores;   
        
        
    }
    public void reset(){
        this.sp = new Stack();
//        this.pc = 0;
//        this.acc = this.re = 0;
//        this.ri = "";
    }

    public void run(){
        short opcode = 0;
        int mod1=0, mod2=0;
        byte op1, op2;

//        if (this.modOp == 0) {
        opcode = Short.parseShort(this.memoria.load(pc++));
        String regs;
        String reg;
        String reg2;
        String mod_end;
        String mod_end2;
        String end_s;
        short end, regShort, reg2Short;

            //System.out.println("opcode: " + opcode);
            
            //Verifica o modo de enderaçamento
//            if (opcode > 31 && opcode < 128) {
//                if (opcode > 97) {
//                        opcode -= 96;
//                        mod1 = 1;
//                        mod2 = 1;
//                }
//                else if (opcode > 63) {
//                    opcode -= 64;
//                    mod1 = 1;
//                    mod2 = 1;
//                }
//                else {
//                    opcode -= 32;
//                    mod1 = 1;
//                }
//            }
//            else if (opcode >= 128) {
//                opcode -= 128;
//                if (opcode == 13 || opcode == 45) {//copy
//                    mod2 = 2;
//                    if (opcode == 45) {
//                        opcode -= 32;
//                        mod1 = 1;
//                    }
//                }
//                else { //imediato
//                    mod1 = 2;
//                }
//            }
            
            switch(opcode) {
                
                //NOP
                case 0:
                    inst.nop();
                    break;
                    
                //CCC
                case 1:
                    regs = memoria.load(pc++);                          
                    if (regs.charAt(0) == '1') 
                        this.N = true;
                    if (regs.charAt(1) == '1')
                        this.Z = true;
                    if (regs.charAt(2) == '1')
                        this.V = true;
                    if (regs.charAt(3) == '1')
                        this.C = true;
                    break;
                
                //SCC
                case 2:
                    regs = memoria.load(pc++);
                    if (regs.charAt(0) == '1') 
                        this.N = false;
                    if (regs.charAt(1) == '1')
                        this.Z = false;
                    if (regs.charAt(2) == '1')
                        this.V = false;
                    if (regs.charAt(3) == '1')
                        this.C = false;
                    break;
                    
                //condicional
                case 3:
                    op1 = memoria.loadByte(pc++);
                    re = op1;
                    
                    //Load 2 extra
                    end_s = memoria.load(pc++) + memoria.load(pc++);
                    end = Short.parseShort(end_s, 2);
                    switch(op1) {
                        case 0:
                            pc = inst.br(end);
                            break;
                        case 1:
                            pc = inst.bne_generic(pc, end, Z);
                            break;
                        
                        case 2:
                            pc = inst.beq_generic(pc, end, Z);
                            break;
                            
                        case 3:
                            pc = inst.bne_generic(pc, end, N);
                            break;
                        
                        case 4:
                            pc = inst.beq_generic(pc, end, N);
                            break;
                            
                        case 5:
                            pc = inst.bne_generic(pc, end, V);
                            break;
                            
                        case 6:
                            pc = inst.beq_generic(pc, end, V);
                            break;
                        
                        case 7:
                            pc = inst.bne_generic(pc, end, C);
                            break;
                            
                        case 8:
                            pc = inst.beq_generic(pc, end, C);
                            break;
                            
                        case 9:
                            pc = inst.bge(pc, end, N, V);
                            break;
                            
                        case 10:
                            pc = inst.blt(pc, end, N, V);
                            break;
                            
                        case 11:
                            pc = inst.bgt(pc, end, N, V, Z);
                            break;
                            
                        case 12:
                            pc = inst.ble(pc, end, N, V, Z);
                            break;
                            
                        case 13:
                            pc = inst.bhi(pc, end, C, Z);
                            break;
                            
                        case 14:
                            pc = inst.bls(pc, end, C, Z);
                            break;
                    }
                    
                    break;
                    
                case 4: //#todo
                    pc++; //ignore
                    end_s = this.memoria.load(pc++) + this.memoria.load(pc++);
                    mod_end = end_s.substring(2, 5);
                    reg = end_s.substring(5, 8);
//                    pc = inst.jmp(mod_end, reg);
                    break;
                    
                case 5: //#todo
                    reg = this.memoria.load(pc++);
                    reg = reg.substring(1, 4);
                    end_s = this.memoria.load(pc++) + this.memoria.load(pc++);
//                    pc = inst.sob(end_s, reg);
                    break;
                    
                case 6: //#todo
                    reg = this.memoria.load(pc++);
                    reg2 = reg.substring(1, 4); //UTILIZADO PARA SALVAR DE ONDE VEIO ANTES DO JUMP
                    end_s = this.memoria.load(pc++) + this.memoria.load(pc++);
                    mod_end = end_s.substring(2, 5);
                    reg = end_s.substring(5, 8);
//                    pc = inst.jsr(mod_end, reg, reg2);
                    break;
                    
                case 7: //#TODO
                    reg = this.memoria.load(pc++); //acho que é desprezível
//                    pc = inst.rts();
                    break;
                    
                case 8: //instruções de 1 operando PÁGINA 25
                    op1 = this.memoria.loadByte(pc++);
                    end_s = this.memoria.load(pc++) + this.memoria.load(pc++);
                    mod_end = end_s.substring(2, 5);
                    reg = end_s.substring(5, 8);
                    regShort = Short.parseShort(reg);
                    this.regs.load(regShort);
                    
                    switch(op1) {
                        case 0: //CLR
                            this.regs.store(Integer.toBinaryString(inst.clr(regShort, N, Z)), regShort);
                            break;
                    
                        case 1: //NOT
                            this.regs.store(Integer.toBinaryString(inst.not(regShort, N, Z, V)), regShort);
                            break;
                            
                        case 2: //INC
//                            acc = inst.inc(regShort, N, Z, V, C);
                            break;
                            
                        case 3: //DEC
//                            acc = inst.dec(regShort, N, Z, V, C);
                            break;
                            
                        case 4: //NEG
//                            acc = inst.neg(regShort, N, Z, V, C);
                            break;
                            
                        case 5: //TST
//                            inst.tst(N, Z);
                            break;
                            
                        case 6: //ROR
//                            acc = inst.ror(regShort, N, Z, V, C);
                            break;
                            
                        case 7: //ROL
//                            acc = inst.rol(regShort, N, Z, V, C);
                            break;
                            
                        case 8: //ASR
//                            acc = inst.asr(regShort, N, Z, V, C);
                            break;
                            
                        case 9: //ASL
//                            acc = inst.asl(regShort, N, Z, V, C);
                            break;
                            
                        case 10: //ADC
//                            acc = inst.adc(regShort, N, Z, V, C);
                            break;
                            
                        case 11: //SBC
//                            acc = inst.sbc(regShort, N, Z, V, C);
                            break;
                            
                            
                                
                    }
                    break;
                    
                default: //9 .. 15
                    if (opcode < 15) { //op de 2 enderecos
                        //Encontra os 2 endereços e 2 modos de endereçamento
                        end_s = this.memoria.load(pc++);
                        mod_end = end_s.substring(0, 3);
                        reg = end_s.substring(3, 4);
                        regShort = Short.parseShort(reg);
                        
                        end_s = this.memoria.load(pc++) + this.memoria.load(pc++);
                        reg += end_s.substring(0, 2);
                        mod_end2 = end_s.substring(2, 5);
                        reg2 = end_s.substring(5, 8);
                        reg2Short = Short.parseShort(reg2);
                        
                        switch(opcode) {
                            case 9: //mov
                                inst.mov(regShort, reg2Short, N, Z, V);
                                break;
                            case 10: //add
                                acc = inst.add(regShort, reg2Short, N, Z, V, C);
                                break;
                            case 11: //sub
                                acc = inst.sub(regShort, reg2Short, N, Z, V, C);
                                break;
                            case 12: //cmp
                                inst.cmp(regShort, reg2Short, N, Z, V);;
                                break;
                            case 13: //and
                                inst.and(regShort, reg2Short, N, Z, V);
                                break;
                            case 14: //or
                                inst.or(regShort, reg2Short, N, Z, V);
                                break;
                        }
                        
                    }
                    else { //halt
                        pc++;
//                        this.inst.hlt();    
                    }
                    break;
                    
            }
 
        ri = memoria.load(pc); //atualiza Registrador de Instrucao
    }
    
}