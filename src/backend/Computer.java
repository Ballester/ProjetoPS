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

    public void reset() {
        this.sp = new Stack();
//        this.pc = 0;
//        this.acc = this.re = 0;
//        this.ri = "";
    }

    /**
     * @params mod exemplo: "001"
     *
     * @return o valor do registrador que estava sendo buscado
     */
    public short convertModEndToValue(String reg, String mod) {
        short modShort = Short.parseShort(mod, 2);
        short regShort = Short.parseShort(reg, 2);
        String regValue = this.regs.load(regShort);
        short regValueShort = Short.parseShort(regValue, 2);
        switch (modShort) {
            case 0:
//                reg = this.regs.load(regValueShort);
                break;
            case 1: //Pós-incrementado
                regValue = this.memoria.load(regValueShort);
                regValueShort = Short.parseShort(regValue, 2);
                break;
            case 2: //Pré-decrementado
                regValueShort -= 2;
                this.regs.store(Integer.toBinaryString(regValueShort), regShort);
                reg = this.memoria.load(regValueShort);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
        }
        return regValueShort;
    }
    
    public void hierarchyStore(String value, short end, String mod_end) {
        short modShort = Short.parseShort(mod_end, 2);
        switch(modShort) {
            case 0:
                this.regs.store(value, end);
                break;
            case 1:
                this.memoria.store(value, end);
                this.regs.store(Integer.toBinaryString(Short.parseShort(this.memoria.load(end))+2), end);
                break;
            case 2:
                this.memoria.store(value, end);
                break;
            //todo mais modos de endereçamento
        }
    }

    public void run() {
        short opcode = 0;
        int mod1 = 0, mod2 = 0;
        byte operation, op1, op2;

        System.out.println("PC:" + pc);
        
        opcode = Short.parseShort(this.memoria.load(pc++), 2);
        String regs;
        String reg;
        String reg2;
        String regValue;
        String regValue2;
        String mod_end;
        String mod_end2;
        String end_s;
        short end, regShort, reg2Short, regValueShort, regValue2Short, valueShort;

        System.out.println("Opcode: " + opcode);
        switch (opcode) {
            //NOP
            case 0:
                System.out.println("NOP");
                pc++;
                inst.nop();
                break;

            //CCC
            case 1:
                System.out.println("CCC");
                regs = memoria.load(pc++);
                if (regs.charAt(0) == '1') {
                    this.N = true;
                }
                if (regs.charAt(1) == '1') {
                    this.Z = true;
                }
                if (regs.charAt(2) == '1') {
                    this.V = true;
                }
                if (regs.charAt(3) == '1') {
                    this.C = true;
                }
                break;

            //SCC
            case 2:
                System.out.println("SCC");
                regs = memoria.load(pc++);
                if (regs.charAt(0) == '1') {
                    this.N = false;
                }
                if (regs.charAt(1) == '1') {
                    this.Z = false;
                }
                if (regs.charAt(2) == '1') {
                    this.V = false;
                }
                if (regs.charAt(3) == '1') {
                    this.C = false;
                }
                break;

            //condicional
            case 3:
                operation = memoria.loadByte(pc++);
                re = operation;

                //Load 2 extra
                end_s = memoria.load(pc++) + memoria.load(pc++);
                end = Short.parseShort(end_s, 2);
                switch (operation) {
                    case 0:
                        System.out.println("BR");
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
                operation = this.memoria.loadByte(pc++);
                end_s = this.memoria.load(pc++) + this.memoria.load(pc++);
                mod_end = end_s.substring(2, 5);
                reg = end_s.substring(5, 8);
                regShort = Short.parseShort(reg, 2);
                this.regs.load(regShort);
                
                System.out.println("ModEnd: " + mod_end);
                
                //AQUI MUDA CONFORME O MODO DE ENDERECAMENTO
                valueShort = convertModEndToValue(reg, mod_end);
//                regValueShort = Short.parseShort(valueShort, 2);
                
                System.out.println("Reg: " + reg + " RegShort: " + regShort);

                switch (operation) {
                    case 0: //CLR
                        System.out.println("CLR");
                        this.hierarchyStore(Integer.toBinaryString(inst.clr(valueShort, N, Z)), regShort, mod_end);
//                        this.regs.store(Integer.toBinaryString(inst.clr(valueShort, N, Z)), regShort);
                        break;

                    case 1: //NOT
                        System.out.println("NOT");
                        this.regs.store(Integer.toBinaryString(inst.not(regShort, N, Z, V)), regShort);
                        break;

                    case 2: //INC
                        System.out.println("INC");
                        this.hierarchyStore(Integer.toBinaryString(inst.inc(valueShort, N, Z, V, C)), regShort, mod_end);
                        break;

                    case 3: //DEC
                        System.out.println("DEC");
//                            acc = inst.dec(regShort, N, Z, V, C);
                        break;

                    case 4: //NEG
                        System.out.println("NEG");
//                            acc = inst.neg(regShort, N, Z, V, C);
                        break;

                    case 5: //TST
                        System.out.println("TST");
//                            inst.tst(N, Z);
                        break;

                    case 6: //ROR
                        System.out.println("ROR");
//                            acc = inst.ror(regShort, N, Z, V, C);
                        break;

                    case 7: //ROL
                        System.out.println("ROL");
//                            acc = inst.rol(regShort, N, Z, V, C);
                        break;

                    case 8: //ASR
                        System.out.println("ASR");
//                            acc = inst.asr(regShort, N, Z, V, C);
                        break;

                    case 9: //ASL
                        System.out.println("ASL");
//                            acc = inst.asl(regShort, N, Z, V, C);
                        break;

                    case 10: //ADC
                        System.out.println("ADC");
//                            acc = inst.adc(regShort, N, Z, V, C);
                        break;

                    case 11: //SBC
                        System.out.println("SBC");
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
                    regShort = Short.parseShort(reg, 2);

                    end_s = this.memoria.load(pc++) + this.memoria.load(pc++);
                    reg += end_s.substring(0, 2);
                    mod_end2 = end_s.substring(2, 5);
                    reg2 = end_s.substring(5, 8);
                    reg2Short = Short.parseShort(reg2, 2);
                    
                    valueShort = convertModEndToValue(reg, mod_end);

                    switch (opcode) {
                        case 9: //mov
                            System.out.println("MOV");
                            valueShort = convertModEndToValue(reg, mod_end);
                            this.hierarchyStore(Integer.toBinaryString(valueShort), reg2Short, mod_end2);
//                            inst.mov(regShort, reg2Short, N, Z, V);
                            break;
                        case 10: //add
                            System.out.println("ADD");
                            acc = inst.add(regShort, reg2Short, N, Z, V, C);
                            break;
                        case 11: //sub
                            System.out.println("SUB");
                            acc = inst.sub(regShort, reg2Short, N, Z, V, C);
                            break;
                        case 12: //cmp
                            System.out.println("CMP");
                            inst.cmp(regShort, reg2Short, N, Z, V);
                            ;
                            break;
                        case 13: //and
                            System.out.println("AND");
                            inst.and(regShort, reg2Short, N, Z, V);
                            break;
                        case 14: //or
                            System.out.println("OR");
                            inst.or(regShort, reg2Short, N, Z, V);
                            break;
                    }

                } else { //halt
                    System.out.println("HLT");
                    pc++;
//                        this.inst.hlt();    
                }
                break;

        }

        ri = memoria.load(pc); //atualiza Registrador de Instrucao
    }

}
