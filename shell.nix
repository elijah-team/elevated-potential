{ pkgs ? import <nixpkgs> {} }:
pkgs.mkShell {
  # both included to avoid switching
  # also headless wastes on gh actions
  buildInputs = with pkgs; [ jdk17_headless maven gradle ];
}
