/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

/**
 *
 * @author pedro
 */
public class Memory {
    
    short[] memory;
    public Memory(int size) {
        this.memory = new short[size];
    }
    
    public void store(short value, int pos) {
        this.memory[pos] = value;
    }
    
    public short load(int pos) {
        return this.memory[pos];
    }
    
}
