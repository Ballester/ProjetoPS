/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import static java.lang.System.exit;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author pedro
 */
public class Instructions {
    
    Scanner scanner;
    public Instructions() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * transforms a value from indirect to direct
     * @param memory
     * @return the direct position
     */
//    public short indToDir(Memory memory, short pos) {
//        return memory.load(pos);
//    }
//    
//    public short indToIm(Memory memory, short pos) {
//        return memory.load(memory.load(pos));
//    }
//    
//    public short dirToIm(Memory memory, short pos) {
//        return memory.load(pos);
//    }
    
    
    /**
     * @return
     */
    public void nop() {
        
    }
    
    /**
     * 
     * @param acc: value
     * @param op1: value
     * @return 
     */
    public short add(short acc, short op1) {
        return (short) (acc + op1);
    }
   
    /**
     * 
     * @param op1: valor
     * @return 
     */
    public short br(short op1) {
        return op1;
    } 
    
    public short bne_generic(short pc, short end, boolean reg) {
        if (!reg) {
            return end;
        }
        return pc;
    }
    
    public short beq_generic(short pc, short end, boolean reg) {
        if (reg) {
            return end;
        }
        return pc;
    }
        
    public short bge(short pc, short end, boolean N, boolean V) {
        if (N == V) {
            return end;
        }
        return pc;
    }
    
    public short blt(short pc, short end, boolean N, boolean V) {
        if (N != V) {
            return end;
        }
        return pc;
    }
    
    public short bgt(short pc, short end, boolean N, boolean V, boolean Z) {
        if (N == V && !Z) {
            return end;
        }
        return pc;
    }
    
    public short ble(short pc, short end, boolean N, boolean V, boolean Z) {
        if (N != V || Z) {
            return end;
        }
        return pc;
    }
    
    public short bhi(short pc, short end, boolean C, boolean Z) {
        if (!C && !Z) {
            return end;
        }
        return pc;
    }
    
    public short bls(short pc, short end, boolean C, boolean Z) {
        if (C || Z) {
            return end;
        }
        return pc;
    }
            
    public Stack call(Stack sp, short pc) {
        return (Stack) sp.push(pc);
        
    }
    
    public short divide(short acc, short op1) {
        return (short) (acc/op1);
    }
    
    public short clr(short op1, boolean N, boolean Z) {
        Z = true;
        N = false;
        op1 = 0;
        return op1;
    }
    
    public short not(short op1, boolean N, boolean Z, boolean C) {
        op1 = (short)~op1;
        if (op1 < 0)
            N = true;
        if(op1 == 0)
            Z = true;
        C = true;
        return (short) (op1);
    }
    
    public short inc(short op1, boolean N, boolean Z, boolean V, boolean C) {
        op1 += 1;
        if (op1 < 0)
            N = true;
        if(op1 == 0)
            Z = true;
        if (op1 > 32.767)
            V = true;
        if(V)
            C = true;
            
        return (short) (op1);
    }
 
    public short dec(short op1, boolean N, boolean Z, boolean V, boolean C) {
        op1 -= 1;
        if (op1 < 0)
            N = true;
        if(op1 == 0)
            Z = true;
        if(op1 < -32.768)
            V = true;
        if(V)
            C = true;
        
        return (short) (op1);
    }
    
    public short neg (short op1, boolean N, boolean Z, boolean V, boolean C){
        op1 = (short)-op1;
        if (op1 < 0)
            N = true;
        if(op1 == 0)
            Z = true;
        if(op1 == -32.768)
            V = true;
        if(V)
            C = true;
        
        return op1;
    }
    
    public void tst( boolean N, boolean Z) {
        Z = false;
        N = false;
    }
    
    public short ror(short op1, boolean N, boolean Z, boolean V, boolean C) {
        short mask = 1;
        short lsb = (short)(mask & op1);
        op1 = (short)(lsb >> 1);
        if (lsb == 1)
            C = true;
        else
            C = false;
        
        if (op1 < 0)
            N = true;
        
        if(op1 == 0)
            Z = true;
        
        V = (N ^ C);
        
        return op1;
    }
    
    public short rol(short op1, boolean N, boolean Z, boolean V, boolean C) {
        short mask = 1 << 14;
        short msb = (short)(op1 & mask);
        op1 = (short)(msb << 1);
        if (msb == 1)
            C = true;
        else
            C = false;
        
        if (op1 < 0)
            N = true;
        
        if(op1 == 0)
            Z = true;
        
        V = (N ^ C);
        
        return op1;
    }
    
    public short asr(short op1, boolean N, boolean Z, boolean V, boolean C) {
        short mask = 1;
        short lsb = (short)(mask & op1);
        op1 = (short)(lsb >> 1);
        if (lsb == 1)
            C = true;
        else
            C = false;
        
        if (op1 < 0)
            N = true;
        
        if(op1 == 0)
            Z = true;
        
        V = (N ^ C);
        
        return op1;
    }
    
    public short asl(short op1, boolean N, boolean Z, boolean V, boolean C) {
        short mask = 1 << 14;
        short msb = (short)(op1 & mask);
        op1 = (short)(msb << 1);
        if (msb == 1)
            C = true;
        else
            C = false;
        
        if (op1 < 0)
            N = true;
        
        if(op1 == 0)
            Z = true;
        
        V = (N ^ C);
        
        return op1;
    }
    
    public short adc(short op1, boolean N, boolean Z, boolean V, boolean C) {
        if (op1 < 0)
            N = true;
        if(op1 == 0)
            Z = true;
        if (op1 > 32.767)
            V = true;
        if(V){
            C = true;
            return op1++;
        }
        return op1;            
    }
    
    public short sbc(short op1, boolean N, boolean Z, boolean V, boolean C) {
        if (op1 < 0)
            N = true;
        if(op1 == 0)
            Z = true;
        if (op1 > 32.767)
            V = true;
        if(V){
            C = true;
            return op1--;
        }
        return op1;            
    }
    
    public void mov(short op1, short op2, boolean N, boolean Z, boolean V){
        op2 = op1;
        if (op2 < 0)
            N = true;
        if(op2 == 0)
            Z = true;
        V = false;
    }
    
    public short add(short op1, short op2, boolean N, boolean Z, boolean V, boolean C){
        op2 += op1;
        if (op2 < 0)
            N = true;
        if(op2 == 0)
            Z = true;
        if (op2 > 32.767)
            V = true;
        if(V)
            C = true;
        return op2;
    }
    
    public short sub(short op1, short op2, boolean N, boolean Z, boolean V, boolean C){
        op2 -= op1;
        if (op2 < 0)
            N = true;
        if(op2 == 0)
            Z = true;
        if (op2 < -32.768)
            V = true;
        if(V)
            C = true;
        return op2;
    }
    
    public boolean cmp(short op1, short op2, boolean N, boolean Z, boolean V){
        if ((op1 - op2) == 0)
            return true;
        if (op2 < 0)
            N = true;
        if(op2 == 0)
            Z = true;
        V = false;
        return false;
    }
    
    public short and(short op1, short op2, boolean N, boolean Z, boolean V){
        if (op2 < 0)
            N = true;
        if(op2 == 0)
            Z = true;
        V = false;
        return (short)(op1 & op2);
    }
    
    public short or(short op1, short op2, boolean N, boolean Z, boolean V){
        if (op2 < 0)
            N = true;
        if(op2 == 0)
            Z = true;
        V = false;
        return (short)(op1 | op2);
    }
    
    /**
     * 
     * @param memory
     * @param pos1: direct memory position
     * @param pos2: direct memory position
     */
//    public void copy(Memory memory, int pos1, int pos2) {
//        memory.store(memory.load(pos2), pos1);
//    }
    
    public short mult(short acc, short op1) {
        return (short) (acc*op1);
    }
    
    public String read() {
        return this.scanner.nextLine();
    }
    
    public short ret(Stack sp, short pc) {
        return (short) sp.pop();
    }
    
    public void stop() {
        exit(0);
    }
    
    public short sub(short acc, short op1) {
        return (short) (acc - op1);
    } 
    
    public void write(short op1) {
        System.out.println(op1);
    }
    
    /**
     * 
     * @param memory
     * @param op1: value
     * @param pos: direct memory position
     */
    public void store(Memory memory, String op1, int pos) {
        memory.store(op1, pos);
    }
    
    public void store(Memory memory, String op1, short pos) {
        memory.store(op1, pos);
    }
    
    /**
     * 
     * @param acc
     * @param pos: access memory position 
     */
    public void load(short acc, short pos) {
        acc = pos;
    }
    
    public short load(short pos){
        return pos;
    }
    
    
}
