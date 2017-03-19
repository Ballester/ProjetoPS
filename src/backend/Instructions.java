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
    
    /**
     * 
     * @param memory
     * @param pos1: direct memory position
     * @param pos2: direct memory position
     */
//    public void copy(Memory memory, int pos1, int pos2) {
//        memory.store(memory.load(pos2), pos1);
//    }
    
    public short divide(short acc, short op1) {
        return (short) (acc/op1);
    }
    
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
